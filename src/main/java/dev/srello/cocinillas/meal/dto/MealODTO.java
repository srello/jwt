package dev.srello.cocinillas.meal.dto;

import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.user.dto.AuthorODTO;
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
    private Integer diners;
    private AuthorODTO author;
}
