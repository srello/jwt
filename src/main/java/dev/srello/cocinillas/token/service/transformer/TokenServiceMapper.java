package dev.srello.cocinillas.token.service.transformer;

import dev.srello.cocinillas.token.dto.TokenODTO;
import dev.srello.cocinillas.token.model.Token;
import org.mapstruct.Mapper;

@Mapper
public interface TokenServiceMapper {
    TokenODTO toODTO(Token token);
}
