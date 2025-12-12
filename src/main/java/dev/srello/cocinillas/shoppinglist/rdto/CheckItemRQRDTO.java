package dev.srello.cocinillas.shoppinglist.rdto;

import org.jetbrains.annotations.NotNull;

public record CheckItemRQRDTO(
        @NotNull("Checked must not be null")
        Boolean checked,
        @NotNull("Item id must not be null")
        Long itemId
) {
}
