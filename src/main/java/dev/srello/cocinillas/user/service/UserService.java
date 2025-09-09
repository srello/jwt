package dev.srello.cocinillas.user.service;

import dev.srello.cocinillas.user.dto.UserODTO;

public interface UserService {
    UserODTO getByUsername(String username);
}
