package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.shared.enums.InteractionType;

public record RecipeInteractionRSRDTO(
        Long id,
        Long userId,
        Long recipeId,
        InteractionType type
) {
}
