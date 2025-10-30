package dev.srello.cocinillas.recipe.rdto;

import java.util.List;

public record RecipeSummaryRSRDTO(
        Long id,
        String name,
        String description,
        List<String> imageUrls,
        Integer totalDuration,
        Double calories,
        Long likes,
        Boolean isLiked,
        Boolean isSaved

) {
}
