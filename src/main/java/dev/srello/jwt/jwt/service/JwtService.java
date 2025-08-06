package dev.srello.jwt.jwt.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.jwt.jwt.enums.JwtValidity;
import dev.srello.jwt.token.enums.TokenType;
import dev.srello.jwt.user.dto.UserODTO;

import java.text.ParseException;

public interface JwtService {
    SignedJWT generateToken(UserODTO userODTO, TokenType tokenType) throws JOSEException, ParseException;

    SignedJWT getDecodedJwt(String jwt);

    JwtValidity verifyJwt(SignedJWT signedJWT);

    String getEmailFromJwtToken(SignedJWT signedJWT);
}
