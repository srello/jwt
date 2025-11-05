package dev.srello.cocinillas.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MealIDTO {
    private String name;
    private Long userId;
    private Long recipeId;
    private LocalDateTime dateTime;
}
