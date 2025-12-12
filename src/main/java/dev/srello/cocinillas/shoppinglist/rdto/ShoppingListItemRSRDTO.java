package dev.srello.cocinillas.shoppinglist.rdto;

import dev.srello.cocinillas.product.rdto.ProductRSRDTO;

public record ShoppingListItemRSRDTO(
        Long id,
        ProductRSRDTO product,
        Double quantity,
        Boolean checked
) {

}
