package dev.srello.cocinillas.meal.rdto;

import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record MealRQRDTO(
        @NotEmpty(message = "Name is required")
        String name,
        @NotNull("Recipes are required")
        List<Long> recipeIds,
        @NotNull(value = "Date time is required")
        LocalDateTime dateTime,
        @NotNull(value = "Diners is required")
        Integer diners
) {
}
