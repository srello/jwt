package dev.srello.cocinillas.user.service;

import dev.srello.cocinillas.user.dto.UserIDTO;
import dev.srello.cocinillas.user.dto.UserODTO;

public interface UserService {
    UserODTO getByUsername(String username);

    UserODTO getByEmail(String email);

    UserODTO createUser(UserIDTO userIDTO);

    UserODTO updateUser(UserODTO userODTO);
}
