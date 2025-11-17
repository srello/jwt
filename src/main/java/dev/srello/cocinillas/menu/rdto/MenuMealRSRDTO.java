package dev.srello.cocinillas.menu.rdto;

import dev.srello.cocinillas.recipe.rdto.RecipeSummaryRSRDTO;

import java.time.LocalTime;
import java.util.List;

public record MenuMealRSRDTO(
        Long id,
        String name,
        List<RecipeSummaryRSRDTO> recipes,
        LocalTime hour,
        Integer dayIndex
) {
}
