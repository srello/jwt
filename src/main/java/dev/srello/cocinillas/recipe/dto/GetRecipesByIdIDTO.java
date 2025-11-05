package dev.srello.cocinillas.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetRecipesByIdIDTO {
    private List<Long> recipeIds;
    private Long userId;
}
