package dev.srello.cocinillas.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EditRecipeIDTO {
    private Long id;
    private RecipeIDTO recipeIDTO;
}
