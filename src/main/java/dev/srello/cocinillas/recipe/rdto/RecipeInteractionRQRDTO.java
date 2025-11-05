package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.recipe.enums.RecipeInteractionType;
import jakarta.validation.constraints.NotEmpty;

public record RecipeInteractionRQRDTO(
        @NotEmpty(message = "Recipe id is required")
        Long recipeId,
        @NotEmpty(message = "Recipe interaction type is required")
        RecipeInteractionType type
) {
}
