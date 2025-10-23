package dev.srello.cocinillas.auth.rdto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record ResetPasswordRQRDTO(
        @NotEmpty(message = "Email is required")
        String token,

        @NotEmpty(message = "Password is required")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?=\\S+$).{8,}$",
                message = "Password must be at least 8 characters and contain at least one uppercase letter, one number, and one special character"
        )
        String password) {
}
