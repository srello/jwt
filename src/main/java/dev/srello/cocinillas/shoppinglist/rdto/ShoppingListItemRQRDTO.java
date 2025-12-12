package dev.srello.cocinillas.shoppinglist.rdto;

import org.jetbrains.annotations.NotNull;

public record ShoppingListItemRQRDTO(
        @NotNull("Product id must not be null.")
        Long productId,
        @NotNull("Quantity must not be null.")
        Double quantity
) {

}
