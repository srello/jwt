package dev.srello.cocinillas.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ForgotPasswordIDTO {
    private String email;
    private String password;
}
