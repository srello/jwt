package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.recipe.enums.RecipeInteractionType;
import org.jetbrains.annotations.NotNull;

public record RecipeInteractionRQRDTO(
        @NotNull("Recipe id is required")
        Long recipeId,
        @NotNull("Recipe interaction type is required")
        RecipeInteractionType type
) {
}
