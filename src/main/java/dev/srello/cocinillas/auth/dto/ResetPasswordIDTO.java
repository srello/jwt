package dev.srello.cocinillas.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResetPasswordIDTO {
    private String token;
    private String password;
}
