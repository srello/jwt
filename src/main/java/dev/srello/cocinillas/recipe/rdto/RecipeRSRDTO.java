package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.product.rdto.ProductRSRDTO;
import dev.srello.cocinillas.recipe.enums.RecipeVisibility;
import dev.srello.cocinillas.tags.rdto.TagRSRDTO;

import java.util.List;

public record RecipeRSRDTO(
        Long id,
        String name,
        List<ProductRSRDTO> ingredients,
        RecipeVisibility visibility,
        List<TagRSRDTO> tags,
        List<String> imageUrls
) {
}
