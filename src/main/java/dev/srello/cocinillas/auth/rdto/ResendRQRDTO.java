package dev.srello.cocinillas.auth.rdto;

import jakarta.validation.constraints.NotEmpty;

public record ResendRQRDTO(
        @NotEmpty(message = "Email is required")
        String email) {
}
