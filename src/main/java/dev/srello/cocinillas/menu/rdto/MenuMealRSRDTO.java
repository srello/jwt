package dev.srello.cocinillas.menu.rdto;

import java.time.LocalTime;

public record MenuMealRSRDTO(
        Long id,
        String name,
        Long recipeId,
        LocalTime hour,
        Integer dayIndex
) {
}
