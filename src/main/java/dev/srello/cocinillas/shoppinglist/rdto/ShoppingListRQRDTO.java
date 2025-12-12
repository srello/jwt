package dev.srello.cocinillas.shoppinglist.rdto;

import java.time.LocalDateTime;

public record ShoppingListRQRDTO(
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
