package dev.srello.cocinillas.token.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.Ed25519Signer;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.jwk.gen.OctetKeyPairGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.BaseTestClass;
import dev.srello.cocinillas.core.exception.RequestException;
import dev.srello.cocinillas.token.dto.TokenODTO;
import dev.srello.cocinillas.token.model.Token;
import dev.srello.cocinillas.token.repository.TokenRepository;
import dev.srello.cocinillas.token.service.transformer.TokenServiceTransformer;
import dev.srello.cocinillas.user.dto.UserODTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;

import static com.nimbusds.jose.JWSAlgorithm.EdDSA;
import static com.nimbusds.jose.jwk.Curve.Ed25519;
import static com.nimbusds.jose.jwk.KeyUse.SIGNATURE;
import static com.nimbusds.jose.util.Base64.encode;
import static dev.srello.cocinillas.shared.dates.DateConverter.dateToLocalDateTime;
import static dev.srello.cocinillas.shared.process.HashUtils.hashString;
import static dev.srello.cocinillas.token.enums.TokenType.AUTHORIZATION;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenServiceTest extends BaseTestClass {
    public static final String MOCK_KEY_ID = "mock-key-id";
    @InjectMocks
    TokenServiceImpl tokenService;

    @Mock
    TokenRepository tokenRepository;
    @Mock
    TokenServiceTransformer tokenServiceTransformer;
    @Captor
    ArgumentCaptor<Token> tokenCaptor;

    private static OctetKeyPair generateMockOctetKeyPair() throws JOSEException {
        return new OctetKeyPairGenerator(Ed25519)
                .keyUse(SIGNATURE)
                .keyID(MOCK_KEY_ID)
                .algorithm(EdDSA)
                .generate();
    }

    private static SignedJWT generateMockSignedJWT(OctetKeyPair jwk) throws JOSEException {
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(EdDSA)
                .keyID(jwk.getKeyID())
                .build(), new JWTClaimsSet.Builder().build());
        JWSSigner signer = new Ed25519Signer(jwk);
        signedJWT.sign(signer);
        return signedJWT;
    }

    @Test
    void shouldSucceed_deleteAllTokensFromUser() {
        doNothing().when(tokenRepository).deleteAllTokensFromUser(1);

        tokenService.deleteAllTokensFromUser(1);

        verify(tokenRepository).deleteAllTokensFromUser(1);
    }

    @Test
    void shouldSucceed_deleteAllTokensFromUserAndType() {
        doNothing().when(tokenRepository).deleteAllTokensFromUserAndType(1, AUTHORIZATION);

        tokenService.deleteAllTokensFromUserAndType(1, AUTHORIZATION);

        verify(tokenRepository).deleteAllTokensFromUserAndType(1, AUTHORIZATION);
    }

    @Test
    void shouldSucceed_getTokenByHash() {
        String hash = generateData(String.class);
        Token token = generateData(Token.class);
        TokenODTO tokenODTO = generateData(TokenODTO.class);

        doReturn(of(token)).when(tokenRepository).findByHash(hash);
        doReturn(tokenODTO).when(tokenServiceTransformer).toODTO(token);

        TokenODTO result = tokenService.getTokenByHash(hash);

        assertNotNull(result);
        assertEquals(tokenODTO, result);

        verify(tokenRepository).findByHash(hash);
        verify(tokenServiceTransformer).toODTO(token);
    }

    @Test
    void shouldFail_getTokenByHashInvalidToken() {
        String hash = generateData(String.class);
        Token token = generateData(Token.class);

        doReturn(empty()).when(tokenRepository).findByHash(hash);

        Executable executable = () -> tokenService.getTokenByHash(hash);
        assertThrows(RequestException.class, executable);

        verify(tokenRepository).findByHash(hash);
        verify(tokenServiceTransformer, never()).toODTO(token);
    }

    @Test
    void shouldSucceed_createToken() throws JOSEException {
        OctetKeyPair jwk = generateMockOctetKeyPair();
        String encodedJwk = encode(jwk.toJSONString().getBytes()).toString();
        UserODTO userODTO = generateData(UserODTO.class);
        SignedJWT signedJWT = generateMockSignedJWT(jwk);
        String signedJWTHash = hashString(signedJWT.serialize());
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.toInstant().plus(30, DAYS).toEpochMilli());
        Token token = generateData(Token.class);

        doReturn(token).when(tokenRepository).saveAndFlush(any());

        tokenService.createToken(jwk, userODTO, AUTHORIZATION, issuedAt, expiration, signedJWT);

        verify(tokenRepository).saveAndFlush(tokenCaptor.capture());
        Token result = tokenCaptor.getValue();
        assertEquals(jwk.getKeyID(), result.getJwtID());
        assertEquals(encodedJwk, result.getJwk());
        assertEquals(userODTO.getId(), result.getUserId());
        assertEquals(dateToLocalDateTime(issuedAt), result.getIssuedAt());
        assertEquals(dateToLocalDateTime(expiration), result.getExpiresAt());
        assertEquals(AUTHORIZATION, result.getTokenType());
        assertEquals(signedJWTHash, result.getHash());
    }
}
