package dev.srello.cocinillas.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MealIDTO {
    private String name;
    private Long userId;
    private List<Long> recipeIds;
    private LocalDateTime dateTime;
    private Integer diners;
}
