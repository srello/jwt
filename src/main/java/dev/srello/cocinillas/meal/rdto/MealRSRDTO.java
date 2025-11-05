package dev.srello.cocinillas.meal.rdto;

import dev.srello.cocinillas.recipe.rdto.RecipeSummaryRSRDTO;

import java.time.LocalDateTime;

public record MealRSRDTO(
        Long id,
        String name,
        Long userId,
        RecipeSummaryRSRDTO recipe,
        LocalDateTime dateTime
) {
}
