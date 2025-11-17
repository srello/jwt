package dev.srello.cocinillas.meal.dto;

import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MealODTO {
    private Long id;
    private String name;
    private Long userId;
    private List<RecipeODTO> recipes;
    private LocalDateTime dateTime;
}
