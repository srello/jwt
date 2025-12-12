package dev.srello.cocinillas.shoppinglist.rdto;

import java.time.LocalDateTime;


public record ShoppingListSummaryRSRDTO(
        Long id,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Boolean completed
) {

}
