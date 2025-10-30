package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.recipe.enums.RecipeVisibility;

import java.util.List;

public record GetRecipesRQRDTO(
        String name,
        List<String> tags,
        List<String> ingredients,
        RecipeVisibility visibility,
        Long userId
) {
}
