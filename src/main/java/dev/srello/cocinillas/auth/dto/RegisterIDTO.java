package dev.srello.cocinillas.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterIDTO {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
}
