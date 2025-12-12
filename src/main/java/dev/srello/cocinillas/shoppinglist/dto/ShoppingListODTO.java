package dev.srello.cocinillas.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ShoppingListODTO {
    private Long id;
    private List<ShoppingListItemODTO> items;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean completed;
}
