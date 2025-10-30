package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.recipe.enums.RecipeInteractionType;

public record RecipeInteractionRSRDTO(
        Long id,
        Long userId,
        Long recipeId,
        RecipeInteractionType type
) {
}
