package dev.srello.cocinillas.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginIDTO {
    private String username;
    private String password;
}
