package dev.srello.cocinillas.shoppinglist.rdto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record AddShoppingListItemsRQRDTO(
        @NotNull("Shopping list id must not be null.")
        Long shoppingListId,
        @NotNull("Quantity must not be null.")
        List<ShoppingListItemRQRDTO> items
) {

}
