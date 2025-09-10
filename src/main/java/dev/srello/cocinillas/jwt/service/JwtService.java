package dev.srello.cocinillas.jwt.service;

import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.jwt.enums.JwtValidity;
import dev.srello.cocinillas.token.enums.TokenType;
import dev.srello.cocinillas.user.dto.UserODTO;

public interface JwtService {
    SignedJWT generateToken(UserODTO userODTO, TokenType tokenType);

    SignedJWT getDecodedJwt(String jwt);

    JwtValidity verifyJwt(SignedJWT signedJWT);

    String getUsernameFromJwtToken(SignedJWT signedJWT);
}
