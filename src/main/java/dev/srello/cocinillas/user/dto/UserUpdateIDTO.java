package dev.srello.cocinillas.user.dto;

import dev.srello.cocinillas.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateIDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private Role role;
}
