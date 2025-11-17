package dev.srello.cocinillas.menu.rdto;

import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.List;

public record MenuMealRQRDTO(
        @NotEmpty(message = "Name is required.")
        String name,
        @NotNull("Recipes are required")
        List<Long> recipeIds,
        @NotNull("Hour is required")
        LocalTime hour,
        @NotEmpty(message = "Day index is required.")
        Integer dayIndex
) {
}
