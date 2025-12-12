package dev.srello.cocinillas.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShoppingListItemIDTO {
    private Long productId;
    private Double quantity;
}
