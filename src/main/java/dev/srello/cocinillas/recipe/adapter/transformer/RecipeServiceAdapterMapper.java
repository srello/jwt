package dev.srello.cocinillas.recipe.adapter.transformer;

import dev.srello.cocinillas.recipe.dto.GetRecipesByIdIDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RecipeServiceAdapterMapper {
    GetRecipesByIdIDTO toGetRecipesByIdIDTO(List<Long> recipeIds, Long userId);
}
