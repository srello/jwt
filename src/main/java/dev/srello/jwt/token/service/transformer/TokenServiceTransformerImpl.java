package dev.srello.jwt.token.service.transformer;

import dev.srello.jwt.token.dto.TokenODTO;
import dev.srello.jwt.token.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceTransformerImpl implements TokenServiceTransformer{
    private final TokenServiceMapper tokenServiceMapper;
    @Override
    public TokenODTO toODTO(Token token) {
        return tokenServiceMapper.toODTO(token);
    }
}
