package dev.srello.cocinillas.token.service.transformer;

import dev.srello.cocinillas.token.dto.TokenODTO;
import dev.srello.cocinillas.token.model.Token;

public interface TokenServiceTransformer {
    TokenODTO toODTO(Token token);
}
