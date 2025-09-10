package dev.srello.cocinillas.auth.controller.transformer;

import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.auth.rdto.LoginRQRDTO;
import dev.srello.cocinillas.auth.rdto.RegisterRQRDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AuthControllerTransformerMapper {
    LoginIDTO toLoginIDTO(LoginRQRDTO loginRQRDTO);

    RegisterIDTO toRegisterIDTO(RegisterRQRDTO registerRQRDTO);
}
