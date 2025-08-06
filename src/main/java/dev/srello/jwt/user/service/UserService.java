package dev.srello.jwt.user.service;

import dev.srello.jwt.user.dto.UserODTO;

public interface UserService {
    UserODTO getByUsername(String username);
}
