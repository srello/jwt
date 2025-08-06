package dev.srello.jwt.token.service;

import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.jwt.core.exception.RequestException;
import dev.srello.jwt.token.dto.TokenODTO;
import dev.srello.jwt.token.enums.TokenType;
import dev.srello.jwt.token.model.Token;
import dev.srello.jwt.token.repository.TokenRepository;
import dev.srello.jwt.token.service.transformer.TokenServiceTransformer;
import dev.srello.jwt.user.dto.UserODTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static dev.srello.jwt.core.messages.Messages.Error.TOKEN_INVALID;
import static dev.srello.jwt.shared.dates.DateConverter.dateToLocalDateTime;
import static dev.srello.jwt.shared.process.HashUtils.hashString;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{
    private final TokenRepository tokenRepository;
    private final TokenServiceTransformer tokenServiceTransformer;
    @Override
    public void deleteAllTokensFromUser(Integer userId) {
        tokenRepository.deleteAllTokensFromUser(userId);
    }
    @Override
    public void deleteAllTokensFromUserAndType(Integer userId, TokenType tokenType) {
        tokenRepository.deleteAllTokensFromUserAndType(userId, tokenType);
    }

    @Override
    public TokenODTO getTokenByHash(String hash) {
        Token token = tokenRepository.findByHash(hash).orElseThrow(() -> new RequestException(UNAUTHORIZED, TOKEN_INVALID));
        return tokenServiceTransformer.toODTO(token);
    }

    @Override
    public void createToken(OctetKeyPair jwk, UserODTO userODTO, TokenType tokenType, Date issuedAt, Date expiration, SignedJWT signedJWT) {
        Token token = Token.builder()
                .jwtID(jwk.getKeyID())
                .jwk(Base64.encode(jwk.toJSONString().getBytes()).toString())
                .userId(userODTO.getId())
                .issuedAt(dateToLocalDateTime(issuedAt))
                .expiresAt(dateToLocalDateTime(expiration))
                .tokenType(tokenType)
                .hash(hashString(signedJWT.serialize()))
                .build();
        tokenRepository.saveAndFlush(token);
    }
}
