package dev.srello.jwt.user.controller.transformer;

import dev.srello.jwt.user.dto.UserODTO;
import dev.srello.jwt.user.rdto.UserRSRDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserControllerMapper {
    UserRSRDTO toRSRDTO(UserODTO userODTO);
}
