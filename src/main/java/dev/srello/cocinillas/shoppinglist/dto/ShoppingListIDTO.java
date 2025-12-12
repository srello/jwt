package dev.srello.cocinillas.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ShoppingListIDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean completed;
    private Long authorId;
}
