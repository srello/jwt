package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.recipe.enums.RecipeVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecipeIDTO {

    List<String> tags;
    List<String> ingredients;
    RecipeVisibility visibility;
    private String name;

}
