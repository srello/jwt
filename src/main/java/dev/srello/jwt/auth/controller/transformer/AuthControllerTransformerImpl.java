package dev.srello.jwt.auth.controller.transformer;

import dev.srello.jwt.auth.dto.LoginIDTO;
import dev.srello.jwt.auth.rdto.LoginRQRDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthControllerTransformerImpl implements AuthControllerTransformer {
    private final AuthControllerTransformerMapper authControllerTransformerMapper;
    @Override
    public LoginIDTO toIDTO(@NonNull LoginRQRDTO loginRQRDTO) {
        return authControllerTransformerMapper.toIDTO(loginRQRDTO);
    }
}
