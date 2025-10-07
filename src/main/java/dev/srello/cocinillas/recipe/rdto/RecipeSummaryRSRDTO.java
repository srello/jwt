package dev.srello.cocinillas.recipe.rdto;

import java.util.List;

public record RecipeSummaryRSRDTO(
        Long id,
        String name,
        List<String> imageUrls,
        Integer totalDuration,
        Double calories
) {
}
