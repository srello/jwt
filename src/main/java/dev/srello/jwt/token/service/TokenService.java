package dev.srello.jwt.token.service;

import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.jwt.token.dto.TokenODTO;
import dev.srello.jwt.token.enums.TokenType;
import dev.srello.jwt.user.dto.UserODTO;

import java.util.Date;

public interface TokenService {
    void deleteAllTokensFromUser(Integer userId);

    void deleteAllTokensFromUserAndType(Integer userId, TokenType tokenType);

    TokenODTO getTokenByHash(String hash);

    void createToken(OctetKeyPair jwk, UserODTO userODTO, TokenType tokenType, Date issuedAt, Date expiration, SignedJWT signedJWT);
}
