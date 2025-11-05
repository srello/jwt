package dev.srello.cocinillas.meal.dto;

import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MealODTO {
    private Long id;
    private String name;
    private Long userId;
    private RecipeODTO recipe;
    private LocalDateTime dateTime;
}
