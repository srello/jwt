package dev.srello.cocinillas.auth.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.BaseTestClass;
import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.cookie.service.CookieService;
import dev.srello.cocinillas.core.exception.RequestException;
import dev.srello.cocinillas.jwt.service.JwtService;
import dev.srello.cocinillas.token.service.TokenService;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.service.UserService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;

import static dev.srello.cocinillas.jwt.enums.JwtValidity.INVALID;
import static dev.srello.cocinillas.jwt.enums.JwtValidity.VALID;
import static dev.srello.cocinillas.token.enums.TokenType.*;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest extends BaseTestClass {
    @InjectMocks
    AuthServiceImpl authService;
    @Mock
    UserService userService;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtService jwtService;
    @Mock
    CookieService cookieService;
    @Mock
    TokenService tokenService;

    @Test
    void shouldSucceed_login() throws ParseException, JOSEException {
        LoginIDTO loginIDTO = generateData(LoginIDTO.class);
        MockHttpServletResponse response = generateData(MockHttpServletResponse.class);
        UserODTO userODTO = generateData(UserODTO.class);
        SignedJWT authJWT = generateData(SignedJWT.class);
        Base64URL[] jwtParts = authJWT.getParsedParts();
        String authJWTStr = format("%s.%s", jwtParts[0], jwtParts[1]);
        String signatureJWTStr = jwtParts[2].toString();
        SignedJWT refreshJWT = generateData(SignedJWT.class);
        Cookie authCookie = generateData(Cookie.class);
        Cookie signatureCookie = generateData(Cookie.class);
        Cookie refreshCookie = generateData(Cookie.class);

        doReturn(userODTO).when(userService).getByUsername(loginIDTO.getUsername());
        doReturn(true).when(passwordEncoder).matches(loginIDTO.getPassword(), userODTO.getPassword());
        doNothing().when(tokenService).deleteAllTokensFromUser(userODTO.getId());
        doReturn(authJWT).when(jwtService).generateToken(userODTO, AUTHORIZATION);
        doReturn(refreshJWT).when(jwtService).generateToken(userODTO, REFRESH);
        doReturn(authCookie).when(cookieService).createCookie(AUTHORIZATION.name(), authJWTStr);
        doReturn(signatureCookie).when(cookieService).createCookie(SIGNATURE.name(), signatureJWTStr);
        doReturn(refreshCookie).when(cookieService).createCookie(REFRESH.name(), refreshJWT.getParsedString());

        UserODTO result = authService.login(loginIDTO, response);

        assertEquals(userODTO, result);
        assertEquals(authCookie.getValue(), response.getCookie(authCookie.getName()).getValue());
        assertEquals(signatureCookie.getValue(), response.getCookie(signatureCookie.getName()).getValue());
        assertEquals(refreshCookie.getValue(), response.getCookie(refreshCookie.getName()).getValue());
        verify(userService).getByUsername(loginIDTO.getUsername());
        verify(passwordEncoder).matches(loginIDTO.getPassword(), userODTO.getPassword());
        verify(tokenService).deleteAllTokensFromUser(userODTO.getId());
        verify(jwtService).generateToken(userODTO, AUTHORIZATION);
        verify(jwtService).generateToken(userODTO, REFRESH);
        verify(cookieService).createCookie(AUTHORIZATION.name(), authJWTStr);
        verify(cookieService).createCookie(SIGNATURE.name(), signatureJWTStr);
        verify(cookieService).createCookie(REFRESH.name(), refreshJWT.getParsedString());
    }

    @Test
    void shouldFail_loginPasswordsDoNotMatch() throws ParseException, JOSEException {
        LoginIDTO loginIDTO = generateData(LoginIDTO.class);
        MockHttpServletResponse response = generateData(MockHttpServletResponse.class);
        UserODTO userODTO = generateData(UserODTO.class);
        Executable executable = () -> authService.login(loginIDTO, response);

        doReturn(userODTO).when(userService).getByUsername(loginIDTO.getUsername());
        doReturn(false).when(passwordEncoder).matches(loginIDTO.getPassword(), userODTO.getPassword());

        assertThrows(RequestException.class, executable);

        verify(userService).getByUsername(loginIDTO.getUsername());
        verify(passwordEncoder).matches(loginIDTO.getPassword(), userODTO.getPassword());
        verify(tokenService, never()).deleteAllTokensFromUser(userODTO.getId());
        verify(jwtService, never()).generateToken(userODTO, AUTHORIZATION);
        verify(jwtService, never()).generateToken(userODTO, REFRESH);
        verify(cookieService, never()).createCookie(any(), any());
        verify(cookieService, never()).createCookie(any(), any());
        verify(cookieService, never()).createCookie(any(), any());
    }

    @Test
    void shouldSucceed_refresh() throws ParseException, JOSEException {
        MockHttpServletResponse response = generateData(MockHttpServletResponse.class);
        SignedJWT refreshToken = generateData(SignedJWT.class);
        SignedJWT authToken = generateData(SignedJWT.class);
        Base64URL[] jwtParts = authToken.getParsedParts();
        String authJWTStr = format("%s.%s", jwtParts[0], jwtParts[1]);
        String signatureJWTStr = jwtParts[2].toString();
        UserODTO userODTO = generateData(UserODTO.class);
        Cookie authCookie = generateData(Cookie.class);
        Cookie signatureCookie = generateData(Cookie.class);

        doReturn(VALID).when(jwtService).verifyJwt(refreshToken);
        doReturn(authToken).when(jwtService).generateToken(userODTO, AUTHORIZATION);
        doNothing().when(tokenService).deleteAllTokensFromUserAndType(userODTO.getId(), AUTHORIZATION);
        doReturn(authCookie).when(cookieService).createCookie(AUTHORIZATION.name(), authJWTStr);
        doReturn(signatureCookie).when(cookieService).createCookie(SIGNATURE.name(), signatureJWTStr);

        authService.refresh(userODTO, response,refreshToken);

        verify(jwtService).verifyJwt(refreshToken);
        verify(jwtService).generateToken(userODTO, AUTHORIZATION);
        verify(tokenService).deleteAllTokensFromUserAndType(userODTO.getId(), AUTHORIZATION);
        verify(cookieService).createCookie(AUTHORIZATION.name(), authJWTStr);
        verify(cookieService).createCookie(SIGNATURE.name(), signatureJWTStr);
    }

    @Test
    void shouldFail_refreshInvalidToken() throws ParseException, JOSEException {
        MockHttpServletResponse response = generateData(MockHttpServletResponse.class);
        SignedJWT refreshToken = generateData(SignedJWT.class);
        UserODTO userODTO = generateData(UserODTO.class);

        doReturn(INVALID).when(jwtService).verifyJwt(refreshToken);

        Executable executable = () -> authService.refresh(userODTO, response, refreshToken);
        assertThrows(RequestException.class, executable);

        verify(jwtService).verifyJwt(refreshToken);
        verify(jwtService, never()).generateToken(any(), any());
        verify(tokenService, never()).deleteAllTokensFromUserAndType(any(), any());
        verify(cookieService, never()).createCookie(any(), any());
        verify(cookieService, never()).createCookie(any(), any());
    }

    @Test
    void shouldSucceed_logout(){
        MockHttpServletResponse response = generateData(MockHttpServletResponse.class);
        UserODTO userODTO = generateData(UserODTO.class);
        Cookie deletedAuthCookie = new Cookie(AUTHORIZATION.name(), null);
        deletedAuthCookie.setMaxAge(0);
        Cookie deletedSignatureCookie = new Cookie(SIGNATURE.name(), null);
        deletedSignatureCookie.setMaxAge(0);
        Cookie deletedRefreshCookie = new Cookie(REFRESH.name(), null);
        deletedRefreshCookie.setMaxAge(0);

        doNothing().when(tokenService).deleteAllTokensFromUser(userODTO.getId());
        doReturn(deletedAuthCookie).when(cookieService).deleteCookie(AUTHORIZATION.name());
        doReturn(deletedSignatureCookie).when(cookieService).deleteCookie(SIGNATURE.name());
        doReturn(deletedRefreshCookie).when(cookieService).deleteCookie(REFRESH.name());

        authService.logout(userODTO, response);

        assertTrue(stream(response.getCookies())
                .allMatch(cookie -> cookie.getMaxAge() == 0));

        verify(tokenService).deleteAllTokensFromUser(userODTO.getId());
        verify(cookieService).deleteCookie(AUTHORIZATION.name());
        verify(cookieService).deleteCookie(SIGNATURE.name());
        verify(cookieService).deleteCookie(REFRESH.name());
    }
}
