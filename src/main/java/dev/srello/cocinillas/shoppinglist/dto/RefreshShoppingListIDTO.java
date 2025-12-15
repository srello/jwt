package dev.srello.cocinillas.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RefreshShoppingListIDTO {
    private Long shoppingListId;
    private Long userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
