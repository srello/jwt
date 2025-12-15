package dev.srello.cocinillas.meal.rdto;

import dev.srello.cocinillas.recipe.rdto.RecipeSummaryRSRDTO;
import dev.srello.cocinillas.user.rdto.AuthorRSRDTO;

import java.time.LocalDateTime;
import java.util.List;

public record MealRSRDTO(
        Long id,
        String name,
        AuthorRSRDTO author,
        List<RecipeSummaryRSRDTO> recipes,
        LocalDateTime dateTime,
        Integer diners
) {
}
