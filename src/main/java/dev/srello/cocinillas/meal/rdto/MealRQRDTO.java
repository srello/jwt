package dev.srello.cocinillas.meal.rdto;

import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public record MealRQRDTO(
        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Recipe id is required")
        Long recipeId,
        @NotNull(value = "Date time is required")
        LocalDateTime dateTime
) {
}
