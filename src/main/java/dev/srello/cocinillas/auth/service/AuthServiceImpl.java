package dev.srello.cocinillas.auth.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.cookie.service.CookieService;
import dev.srello.cocinillas.core.exception.RequestException;
import dev.srello.cocinillas.jwt.service.JwtService;
import dev.srello.cocinillas.token.service.TokenService;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import static dev.srello.cocinillas.core.messages.Messages.Error.TOKEN_INVALID;
import static dev.srello.cocinillas.core.messages.Messages.Error.USER_PASSWORD_DONT_MATCH;
import static dev.srello.cocinillas.jwt.enums.JwtValidity.VALID;
import static dev.srello.cocinillas.token.enums.TokenType.*;
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
    @Override
    public UserODTO login(LoginIDTO loginIDTO, HttpServletResponse response) throws ParseException, JOSEException {
        UserODTO userODTO = userService.getByUsername(loginIDTO.getUsername());
        if (!passwordEncoder.matches(loginIDTO.getPassword(), userODTO.getPassword()))
            throw new RequestException(BAD_REQUEST, USER_PASSWORD_DONT_MATCH);

        tokenService.deleteAllTokensFromUser(userODTO.getId());
        SignedJWT authJWT = jwtService.generateToken(userODTO, AUTHORIZATION);
        SignedJWT refreshJWT = jwtService.generateToken(userODTO, REFRESH);
        Base64URL[] jwtParts = authJWT.getParsedParts();
        response.addCookie(cookieService.createCookie(AUTHORIZATION.name(), format("%s.%s", jwtParts[0], jwtParts[1])));
        response.addCookie(cookieService.createCookie(SIGNATURE.name(), jwtParts[2].toString()));
        response.addCookie(cookieService.createCookie(REFRESH.name(), refreshJWT.getParsedString()));

        return userODTO;
    }

    @Override
    public void refresh(UserODTO userODTO, HttpServletResponse response, SignedJWT refreshToken) throws ParseException, JOSEException {
        if(!jwtService.verifyJwt(refreshToken).equals(VALID)){
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
}
