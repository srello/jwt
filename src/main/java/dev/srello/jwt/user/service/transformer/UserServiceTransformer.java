package dev.srello.jwt.user.service.transformer;

import dev.srello.jwt.user.dto.UserODTO;
import dev.srello.jwt.user.model.User;

public interface UserServiceTransformer {
    UserODTO toODTO(User user);
}
