package dev.srello.cocinillas.auth.controller.transformer;

import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.auth.dto.ResetPasswordIDTO;
import dev.srello.cocinillas.auth.rdto.LoginRQRDTO;
import dev.srello.cocinillas.auth.rdto.RegisterRQRDTO;
import dev.srello.cocinillas.auth.rdto.ResetPasswordRQRDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthControllerTransformerImpl implements AuthControllerTransformer {
    private final AuthControllerTransformerMapper authControllerTransformerMapper;

    @Override
    public LoginIDTO toLoginIDTO(@NonNull LoginRQRDTO loginRQRDTO) {
        return authControllerTransformerMapper.toLoginIDTO(loginRQRDTO);
    }

    @Override
    public RegisterIDTO toRegisterIDTO(@NonNull RegisterRQRDTO registerRQRDTO) {
        return authControllerTransformerMapper.toRegisterIDTO(registerRQRDTO);
    }

    @Override
    public ResetPasswordIDTO toResetPasswordIDTO(@NonNull ResetPasswordRQRDTO resetPasswordRQRDTO) {
        return authControllerTransformerMapper.toResetPasswordIDTO(resetPasswordRQRDTO);
    }
}
