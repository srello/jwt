package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.recipe.enums.RecipeInteractionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipeInteractionIDTO {
    private Long userId;
    private Long recipeId;
    private RecipeInteractionType type;
}
