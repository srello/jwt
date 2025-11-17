package dev.srello.cocinillas.recipe.adapter.transformer;

import dev.srello.cocinillas.recipe.dto.GetRecipesByIdIDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecipeServiceAdapterTransformerImpl implements RecipeServiceAdapterTransformer {

    private final RecipeServiceAdapterMapper mapper;

    @Override
    public GetRecipesByIdIDTO toGetRecipesByIdIDTO(List<Long> recipeIds, Long userId) {
        return mapper.toGetRecipesByIdIDTO(recipeIds, userId);
    }
}
