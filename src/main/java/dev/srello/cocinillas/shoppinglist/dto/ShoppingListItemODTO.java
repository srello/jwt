package dev.srello.cocinillas.shoppinglist.dto;

import dev.srello.cocinillas.product.dto.ProductODTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShoppingListItemODTO {
    private Long id;
    private ProductODTO product;
    private Double quantity;
    private Boolean checked;
}
