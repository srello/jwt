package dev.srello.cocinillas.auth.controller.transformer;

import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.auth.dto.ResetPasswordIDTO;
import dev.srello.cocinillas.auth.rdto.LoginRQRDTO;
import dev.srello.cocinillas.auth.rdto.RegisterRQRDTO;
import dev.srello.cocinillas.auth.rdto.ResetPasswordRQRDTO;
import lombok.NonNull;

public interface AuthControllerTransformer {
    LoginIDTO toLoginIDTO(@NonNull LoginRQRDTO loginRQRDTO);

    RegisterIDTO toRegisterIDTO(@NonNull RegisterRQRDTO registerRQRDTO);

    ResetPasswordIDTO toResetPasswordIDTO(@NonNull ResetPasswordRQRDTO resetPasswordRQRDTO);
}
