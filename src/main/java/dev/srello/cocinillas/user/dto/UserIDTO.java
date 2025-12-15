package dev.srello.cocinillas.user.dto;

import dev.srello.cocinillas.settings.dto.SettingsIDTO;
import dev.srello.cocinillas.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserIDTO {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private Role role;
    private SettingsIDTO settings;
}
