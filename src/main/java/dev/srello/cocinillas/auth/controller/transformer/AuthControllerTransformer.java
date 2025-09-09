package dev.srello.cocinillas.auth.controller.transformer;

import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.auth.rdto.LoginRQRDTO;
import lombok.NonNull;

public interface AuthControllerTransformer {
    LoginIDTO toIDTO(@NonNull LoginRQRDTO loginRQRDTO);
}
