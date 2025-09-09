package dev.srello.cocinillas.user.controller.transformer;

import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.rdto.UserRSRDTO;

public interface UserControllerTransformer {
    UserRSRDTO toRSRDTO(UserODTO userODTO);
}
