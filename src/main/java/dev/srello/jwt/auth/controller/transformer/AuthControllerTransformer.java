package dev.srello.jwt.auth.controller.transformer;

import dev.srello.jwt.auth.dto.LoginIDTO;
import dev.srello.jwt.auth.rdto.LoginRQRDTO;
import lombok.NonNull;

public interface AuthControllerTransformer {
    LoginIDTO toIDTO(@NonNull LoginRQRDTO loginRQRDTO);
}
