package dev.srello.cocinillas.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteShoppingListItemIDTO {
    private Long shoppingListId;
    private Long userId;
    private Long itemId;
}
