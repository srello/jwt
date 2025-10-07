package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.product.dto.ProductODTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IngredientODTO {
    private Long id;
    private Double quantity;
    private ProductODTO product;
}
