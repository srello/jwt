package dev.srello.cocinillas.auth.service;

import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.auth.service.transformer.AuthServiceTransformer;
import dev.srello.cocinillas.cookie.service.CookieService;
import dev.srello.cocinillas.core.exception.RequestException;
import dev.srello.cocinillas.email.service.EmailService;
import dev.srello.cocinillas.jwt.enums.JwtValidity;
import dev.srello.cocinillas.jwt.service.JwtService;
import dev.srello.cocinillas.token.service.TokenService;
import dev.srello.cocinillas.user.dto.UserIDTO;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.dto.UserUpdateIDTO;
import dev.srello.cocinillas.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static dev.srello.cocinillas.core.messages.Messages.Error.*;
import static dev.srello.cocinillas.jwt.enums.JwtValidity.VALID;
import static dev.srello.cocinillas.token.enums.TokenType.*;
import static dev.srello.cocinillas.user.enums.Role.NULL;
import static dev.srello.cocinillas.user.enums.Role.USER;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final TokenService tokenService;
    private final AuthServiceTransformer authServiceTransformer;
    private final EmailService emailService;

    @Override
    public UserODTO login(LoginIDTO loginIDTO, HttpServletResponse response) {
        UserODTO userODTO = userService.getByUsername(loginIDTO.getUsername());
        if (NULL.equals(userODTO.getRole()))
            throw new RequestException(BAD_REQUEST, USER_NOT_ENABLED);
        if (!passwordEncoder.matches(loginIDTO.getPassword(), userODTO.getPassword()))
            throw new RequestException(BAD_REQUEST, USER_PASSWORD_DONT_MATCH);

        generateAuthAndRefreshTokens(userODTO, response);

        return userODTO;
    }

    @Override
    public void refresh(UserODTO userODTO, HttpServletResponse response, SignedJWT refreshToken) {
        if (!jwtService.verifyJwt(refreshToken).equals(VALID)) {
            throw new RequestException(UNAUTHORIZED, TOKEN_INVALID);
        }

        tokenService.deleteAllTokensFromUserAndType(userODTO.getId(), AUTHORIZATION);
        SignedJWT authJWT = jwtService.generateToken(userODTO, AUTHORIZATION);
        Base64URL[] jwtParts = authJWT.getParsedParts();
        response.addCookie(cookieService.createCookie(AUTHORIZATION.name(), format("%s.%s", jwtParts[0], jwtParts[1])));
        response.addCookie(cookieService.createCookie(SIGNATURE.name(), jwtParts[2].toString()));
    }

    @Override
    public void logout(UserODTO userODTO, HttpServletResponse response) {
        tokenService.deleteAllTokensFromUser(userODTO.getId());
        response.addCookie(cookieService.deleteCookie(AUTHORIZATION.name()));
        response.addCookie(cookieService.deleteCookie(SIGNATURE.name()));
        response.addCookie(cookieService.deleteCookie(REFRESH.name()));
    }

    @Override
    public void register(RegisterIDTO registerIDTO) {
        try {
            userService.getByEmail(registerIDTO.getEmail());
        } catch (RequestException requestException) {
            //ignored
            UserIDTO userIDTO = authServiceTransformer.toUserIDTO(registerIDTO);
            UserODTO userODTO = userService.createUser(userIDTO);

            generateTokenAndSendEmail(userODTO);
            return;
        }
        throw new RequestException(BAD_REQUEST, USER_WITH_THIS_EMAIL_EXISTS);
    }

    @Override
    public UserODTO confirm(String token, HttpServletResponse response) {
        SignedJWT confirmJwt = jwtService.getDecodedJwt(token);
        JwtValidity confirmJwtValidity = jwtService.verifyJwt(confirmJwt);

        if (!confirmJwtValidity.equals(VALID))
            throw new RequestException(BAD_REQUEST, TOKEN_INVALID);

        String username = jwtService.getUsernameFromJwtToken(confirmJwt);
        UserODTO userODTO = userService.getByUsername(username);
        if (!userODTO.getRole().equals(NULL))
            throw new RequestException(BAD_REQUEST, USER_ALREADY_ENABLED);

        userODTO.setRole(USER);
        UserUpdateIDTO userUpdateIDTO = authServiceTransformer.toUserUpdateIDTO(userODTO);
        UserODTO userODTOUpdated = userService.updateUser(userUpdateIDTO);

        generateAuthAndRefreshTokens(userODTOUpdated, response);

        return userODTOUpdated;
    }

    @Override
    public void resendEmail(String email) {
        UserODTO userODTO = userService.getByEmail(email);
        if (!userODTO.getRole().equals(NULL))
            throw new RequestException(BAD_REQUEST, USER_ALREADY_ENABLED);

        generateTokenAndSendEmail(userODTO);
    }

    private void generateAuthAndRefreshTokens(UserODTO userODTO, HttpServletResponse response) {
        tokenService.deleteAllTokensFromUser(userODTO.getId());
        SignedJWT authJWT = jwtService.generateToken(userODTO, AUTHORIZATION);
        SignedJWT refreshJWT = jwtService.generateToken(userODTO, REFRESH);
        Base64URL[] jwtParts = authJWT.getParsedParts();
        response.addCookie(cookieService.createCookie(AUTHORIZATION.name(), format("%s.%s", jwtParts[0], jwtParts[1])));
        response.addCookie(cookieService.createCookie(SIGNATURE.name(), jwtParts[2].toString()));
        response.addCookie(cookieService.createCookie(REFRESH.name(), refreshJWT.getParsedString()));
    }

    private void generateTokenAndSendEmail(UserODTO userODTO) {
        tokenService.deleteAllTokensFromUser(userODTO.getId());
        SignedJWT signedJWT = jwtService.generateToken(userODTO, CONFIRM);
        emailService.sendConfirmationEmail(userODTO, signedJWT);
    }
}
