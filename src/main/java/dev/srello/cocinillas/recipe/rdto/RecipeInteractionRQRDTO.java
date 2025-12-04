package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.shared.enums.InteractionType;
import org.jetbrains.annotations.NotNull;

public record RecipeInteractionRQRDTO(
        @NotNull("Recipe id is required")
        Long recipeId,
        @NotNull("Recipe interaction type is required")
        InteractionType type
) {
}
