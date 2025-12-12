package dev.srello.cocinillas.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ShoppingListSummaryODTO {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean completed;
}
