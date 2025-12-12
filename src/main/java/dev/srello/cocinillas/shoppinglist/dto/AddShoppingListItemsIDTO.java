package dev.srello.cocinillas.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AddShoppingListItemsIDTO {
    private Long shoppingListId;
    private Long userId;
    private List<ShoppingListItemIDTO> items;
}
