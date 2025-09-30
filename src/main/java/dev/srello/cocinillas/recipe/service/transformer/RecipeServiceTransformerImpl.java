package dev.srello.cocinillas.recipe.service.transformer;

import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeServiceTransformerImpl implements RecipeServiceTransformer {

    private final RecipeServiceMapper mapper;

    @Override
    public Page<RecipeODTO> toRecipesODTO(@NonNull Page<Recipe> recipes) {
        return mapper.toRecipesODTO(recipes);
    }

}
