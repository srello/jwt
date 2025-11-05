package dev.srello.cocinillas.menu.rdto;

import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

public record MenuMealRQRDTO(
        @NotEmpty(message = "Name is required.")
        String name,
        @NotEmpty(message = "Recipe id is required.")
        Long recipeId,
        @NotNull("Hour is required")
        LocalTime hour,
        @NotEmpty(message = "Day index is required.")
        Integer dayIndex
) {
}
