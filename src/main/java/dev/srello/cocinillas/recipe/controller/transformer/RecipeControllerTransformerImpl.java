package dev.srello.cocinillas.recipe.controller.transformer;

import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.rdto.RecipeRQRDTO;
import dev.srello.cocinillas.recipe.rdto.RecipeRSRDTO;
import dev.srello.cocinillas.recipe.rdto.RecipeSummaryRSRDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeControllerTransformerImpl implements RecipeControllerTransformer {

    private final RecipeControllerMapper mapper;

    @Override
    public RecipeIDTO toRecipeIDTO(@NonNull RecipeRQRDTO recipeRQRDTO) {
        return mapper.toRecipeIDTO(recipeRQRDTO);
    }

    @Override
    public Page<RecipeRSRDTO> toRecipeRSRDTO(@NonNull Page<RecipeODTO> recipesODTO) {
        return mapper.toRecipesRSRDTO(recipesODTO);
    }

    @Override
    public Page<RecipeSummaryRSRDTO> toRecipeSummaryRSRDTO(@NonNull Page<RecipeODTO> recipesODTO) {
        return mapper.toRecipeSummaryRSRDTO(recipesODTO);
    }
}
