package dev.srello.cocinillas.user.controller.transformer;

import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.rdto.UserRSRDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserControllerMapper {
    UserRSRDTO toRSRDTO(UserODTO userODTO);
}
