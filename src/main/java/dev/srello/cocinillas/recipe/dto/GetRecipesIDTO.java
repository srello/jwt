package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.recipe.enums.RecipeVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetRecipesIDTO {

    private List<String> tags;
    private List<String> ingredients;
    private RecipeVisibility visibility;
    private String name;
    private Long userId;
    private Boolean onlyUserRecipes;
}
