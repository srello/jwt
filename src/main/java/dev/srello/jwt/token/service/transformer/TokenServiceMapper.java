package dev.srello.jwt.token.service.transformer;

import dev.srello.jwt.token.dto.TokenODTO;
import dev.srello.jwt.token.model.Token;
import org.mapstruct.Mapper;

@Mapper
public interface TokenServiceMapper {
    TokenODTO toODTO(Token token);
}
