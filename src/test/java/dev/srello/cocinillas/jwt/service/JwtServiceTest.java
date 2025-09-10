package dev.srello.cocinillas.jwt.service;

import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.BaseTestClass;
import dev.srello.cocinillas.core.exception.RequestException;
import dev.srello.cocinillas.token.service.TokenService;
import dev.srello.cocinillas.user.dto.UserODTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import static dev.srello.cocinillas.jwt.service.JwtServiceImpl.*;
import static dev.srello.cocinillas.token.enums.TokenType.ANONYMOUS;
import static dev.srello.cocinillas.token.enums.TokenType.AUTHORIZATION;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class JwtServiceTest extends BaseTestClass {
    public static final String SRELLO_DEV = "srello.dev";
    @InjectMocks
    JwtServiceImpl jwtService;
    @Mock
    TokenService tokenService;

    @BeforeEach
    void init() {
        setField(jwtService, "jwtExpiration", 1800);
        setField(jwtService, "issuerUrl", SRELLO_DEV);
        setField(jwtService, "audienceUrl", SRELLO_DEV);
    }

    @Test
    void shouldSucceed_generateAuthToken() throws ParseException {
        UserODTO userODTO = generateData(UserODTO.class);

        doNothing().when(tokenService).createToken(any(OctetKeyPair.class), eq(userODTO), eq(AUTHORIZATION), any(Date.class), any(Date.class), any(SignedJWT.class));

        SignedJWT result = jwtService.generateToken(userODTO, AUTHORIZATION);

        JWTClaimsSet resultClaims = result.getJWTClaimsSet();
        Map<String, Object> resultUserClaims = resultClaims.getJSONObjectClaim(USER_CLAIMS);

        assertEquals(userODTO.getUsername(), resultUserClaims.get(USERNAME_CLAIM));
        assertEquals(userODTO.getRole().name(), resultUserClaims.get(ROLE_CLAIM));
        assertEquals(AUTHORIZATION.name(), resultUserClaims.get(TOKEN_TYPE_CLAIM));
        assertTrue(resultClaims.getExpirationTime().after(new Date()));
        assertTrue(resultClaims.getIssueTime().before(new Date()));
        assertEquals(SRELLO_DEV, resultClaims.getIssuer());
        assertEquals(SRELLO_DEV, resultClaims.getAudience().getFirst());
        assertEquals(userODTO.getId().toString(), resultClaims.getSubject());
        verify(tokenService).createToken(any(OctetKeyPair.class), eq(userODTO), eq(AUTHORIZATION), any(Date.class), any(Date.class), any(SignedJWT.class));
    }

    @Test
    void shouldSucceed_generateAnonymousToken() throws ParseException {
        UserODTO userODTO = generateData(UserODTO.class);

        doNothing().when(tokenService).createToken(any(OctetKeyPair.class), eq(userODTO), eq(ANONYMOUS), any(Date.class), any(Date.class), any(SignedJWT.class));

        SignedJWT result = jwtService.generateToken(userODTO, ANONYMOUS);

        JWTClaimsSet resultClaims = result.getJWTClaimsSet();
        Map<String, Object> resultUserClaims = resultClaims.getJSONObjectClaim(USER_CLAIMS);

        assertTrue(resultUserClaims.isEmpty());
        assertTrue(resultClaims.getExpirationTime().after(new Date((new Date()).toInstant().plus(30, DAYS).toEpochMilli())));
        assertTrue(resultClaims.getIssueTime().before(new Date()));
        assertEquals(SRELLO_DEV, resultClaims.getIssuer());
        assertEquals(SRELLO_DEV, resultClaims.getAudience().getFirst());
        assertEquals(userODTO.getId().toString(), resultClaims.getSubject());
        verify(tokenService).createToken(any(OctetKeyPair.class), eq(userODTO), eq(ANONYMOUS), any(Date.class), any(Date.class), any(SignedJWT.class));
    }

    @Test
    void shouldSucceed_getDecodedToken() {
        UserODTO userODTO = generateData(UserODTO.class);
        doNothing().when(tokenService).createToken(any(OctetKeyPair.class), eq(userODTO), eq(AUTHORIZATION), any(Date.class), any(Date.class), any(SignedJWT.class));
        SignedJWT signedJWT = jwtService.generateToken(userODTO, AUTHORIZATION);

        SignedJWT result = jwtService.getDecodedJwt(signedJWT.getParsedString());

        Base64URL[] parsedParts = signedJWT.getParsedParts();
        Base64URL[] parsedPartsResult = result.getParsedParts();
        assertEquals(3, parsedPartsResult.length);
        assertEquals(parsedParts[0], parsedPartsResult[0]);
        assertEquals(parsedParts[1], parsedPartsResult[1]);
        assertEquals(parsedParts[2], parsedPartsResult[2]);
    }

    @Test
    void shouldSucceed_getDecodedTokenBearer() {
        UserODTO userODTO = generateData(UserODTO.class);
        doNothing().when(tokenService).createToken(any(OctetKeyPair.class), eq(userODTO), eq(AUTHORIZATION), any(Date.class), any(Date.class), any(SignedJWT.class));
        SignedJWT signedJWT = jwtService.generateToken(userODTO, AUTHORIZATION);

        SignedJWT result = jwtService.getDecodedJwt("Bearer " + signedJWT.getParsedString());

        Base64URL[] parsedParts = signedJWT.getParsedParts();
        Base64URL[] parsedPartsResult = result.getParsedParts();
        assertEquals(3, parsedPartsResult.length);
        assertEquals(parsedParts[0], parsedPartsResult[0]);
        assertEquals(parsedParts[1], parsedPartsResult[1]);
        assertEquals(parsedParts[2], parsedPartsResult[2]);
    }

    @Test
    void shouldFail_getDecodedTokenNotParseable() {
        Executable executable = () -> jwtService.getDecodedJwt("Not parseable token");
        assertThrows(RequestException.class, executable);
    }

    @Test
    void shouldSucceed_getUsernameFromToken() {
        UserODTO userODTO = generateData(UserODTO.class);
        doNothing().when(tokenService).createToken(any(OctetKeyPair.class), eq(userODTO), eq(AUTHORIZATION), any(Date.class), any(Date.class), any(SignedJWT.class));
        SignedJWT signedJWT = jwtService.generateToken(userODTO, AUTHORIZATION);

        String result = jwtService.getUsernameFromJwtToken(signedJWT);

        assertEquals(userODTO.getUsername(), result);
    }

    @Test
    void shouldFail_getUsernameFromToken() {
        SignedJWT signedJWT = generateData(SignedJWT.class);
        Executable executable = () -> jwtService.getUsernameFromJwtToken(signedJWT);
        assertThrows(RequestException.class, executable);
    }
}
