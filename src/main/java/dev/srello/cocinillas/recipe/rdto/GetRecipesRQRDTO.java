package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.shared.enums.Visibility;

import java.util.List;

public record GetRecipesRQRDTO(
        String name,
        List<String> tags,
        List<String> ingredients,
        Visibility visibility,
        Long userId
) {
}
