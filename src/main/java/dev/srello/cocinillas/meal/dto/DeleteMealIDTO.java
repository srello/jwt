package dev.srello.cocinillas.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteMealIDTO {
    private Long userId;
    private Long mealId;
}
