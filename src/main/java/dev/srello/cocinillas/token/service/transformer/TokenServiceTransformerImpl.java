package dev.srello.cocinillas.token.service.transformer;

import dev.srello.cocinillas.token.dto.TokenODTO;
import dev.srello.cocinillas.token.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceTransformerImpl implements TokenServiceTransformer {
    private final TokenServiceMapper tokenServiceMapper;

    @Override
    public TokenODTO toODTO(Token token) {
        return tokenServiceMapper.toODTO(token);
    }
}
