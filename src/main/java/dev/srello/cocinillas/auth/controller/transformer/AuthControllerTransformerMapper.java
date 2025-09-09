package dev.srello.cocinillas.auth.controller.transformer;

import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.auth.rdto.LoginRQRDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AuthControllerTransformerMapper {
    LoginIDTO toIDTO(LoginRQRDTO loginRQRDTO);
}
