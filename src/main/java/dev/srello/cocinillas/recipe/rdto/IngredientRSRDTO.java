package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.product.rdto.ProductRSRDTO;

public record IngredientRSRDTO(
        Long id,
        Double quantity,
        ProductRSRDTO product
) {
}
