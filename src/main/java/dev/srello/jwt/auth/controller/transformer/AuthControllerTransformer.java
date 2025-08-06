package dev.srello.jwt.auth.controller.transformer;

import dev.srello.jwt.auth.dto.LoginIDTO;
import dev.srello.jwt.auth.rdto.LoginRQRDTO;
public interface AuthControllerTransformer {
    LoginIDTO toIDTO(LoginRQRDTO loginRQRDTO);
}
