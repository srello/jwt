package dev.srello.cocinillas.shoppinglist.rdto;

import java.time.LocalDateTime;
import java.util.List;


public record ShoppingListRSRDTO(
        Long id,
        List<ShoppingListItemRSRDTO> items,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Boolean completed
) {

}
