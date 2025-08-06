package dev.srello.jwt.user.controller.transformer;

import dev.srello.jwt.user.dto.UserODTO;
import dev.srello.jwt.user.rdto.UserRSRDTO;

public interface UserControllerTransformer {
    UserRSRDTO toRSRDTO(UserODTO userODTO);
}
