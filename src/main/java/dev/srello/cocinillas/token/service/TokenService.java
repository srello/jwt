package dev.srello.cocinillas.token.service;

import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.token.dto.TokenODTO;
import dev.srello.cocinillas.token.enums.TokenType;
import dev.srello.cocinillas.user.dto.UserODTO;

import java.util.Date;

public interface TokenService {
    void deleteAllTokensFromUser(Long userId);

    void deleteAllTokensFromUserAndType(Long userId, TokenType tokenType);

    TokenODTO getTokenByHash(String hash);

    void createToken(OctetKeyPair jwk, UserODTO userODTO, TokenType tokenType, Date issuedAt, Date expiration, SignedJWT signedJWT);
}
