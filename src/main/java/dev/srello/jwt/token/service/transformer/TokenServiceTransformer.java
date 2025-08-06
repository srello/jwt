package dev.srello.jwt.token.service.transformer;

import dev.srello.jwt.token.dto.TokenODTO;
import dev.srello.jwt.token.model.Token;

public interface TokenServiceTransformer {
    TokenODTO toODTO(Token token);
}
