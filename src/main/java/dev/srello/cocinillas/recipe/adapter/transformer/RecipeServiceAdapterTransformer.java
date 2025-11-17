package dev.srello.cocinillas.recipe.adapter.transformer;

import dev.srello.cocinillas.recipe.dto.GetRecipesByIdIDTO;

import java.util.List;

public interface RecipeServiceAdapterTransformer {
    GetRecipesByIdIDTO toGetRecipesByIdIDTO(List<Long> recipeIds, Long userId);
}
