package dev.srello.cocinillas.recipe.rdto;

import jakarta.validation.constraints.NotEmpty;

public record InstructionRQRDTO(
        @NotEmpty(message = "Description can not be empty.")
        String description,
        Double timer,
        Boolean hasImage
) {
}
