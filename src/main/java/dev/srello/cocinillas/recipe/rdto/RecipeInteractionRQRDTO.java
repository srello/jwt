package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.recipe.enums.RecipeInteractionType;

public record RecipeInteractionRQRDTO(
        Long recipeId,
        RecipeInteractionType type
) {
}
