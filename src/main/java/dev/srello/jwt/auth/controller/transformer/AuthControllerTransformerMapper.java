package dev.srello.jwt.auth.controller.transformer;

import dev.srello.jwt.auth.dto.LoginIDTO;
import dev.srello.jwt.auth.rdto.LoginRQRDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AuthControllerTransformerMapper {
    LoginIDTO toIDTO(LoginRQRDTO loginRQRDTO);
}
