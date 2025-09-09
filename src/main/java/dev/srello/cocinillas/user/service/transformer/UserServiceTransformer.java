package dev.srello.cocinillas.user.service.transformer;

import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.model.User;

public interface UserServiceTransformer {
    UserODTO toODTO(User user);
}
