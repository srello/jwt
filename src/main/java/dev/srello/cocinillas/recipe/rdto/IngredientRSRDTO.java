package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.product.dto.ProductODTO;

public record IngredientRSRDTO(
        Long id,
        Double quantity,
        ProductODTO product
) {
}
