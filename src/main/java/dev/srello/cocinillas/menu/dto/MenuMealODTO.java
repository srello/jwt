package dev.srello.cocinillas.menu.dto;

import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MenuMealODTO {
    private Long id;
    private String name;
    private List<RecipeODTO> recipes;
    private LocalTime hour;
    private Integer dayIndex;
}
