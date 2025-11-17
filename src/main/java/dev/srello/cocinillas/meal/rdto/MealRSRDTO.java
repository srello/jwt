package dev.srello.cocinillas.meal.rdto;

import dev.srello.cocinillas.recipe.rdto.RecipeSummaryRSRDTO;

import java.time.LocalDateTime;
import java.util.List;

public record MealRSRDTO(
        Long id,
        String name,
        Long userId,
        List<RecipeSummaryRSRDTO> recipes,
        LocalDateTime dateTime
) {
}
