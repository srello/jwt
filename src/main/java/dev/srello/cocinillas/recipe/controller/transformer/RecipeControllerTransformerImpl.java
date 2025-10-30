package dev.srello.cocinillas.recipe.controller.transformer;

import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.rdto.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeControllerTransformerImpl implements RecipeControllerTransformer {

    private final RecipeControllerMapper mapper;

    @Override
    public GetRecipesIDTO toGetRecipesIDTO(@NonNull GetRecipesRQRDTO getRecipesRQRDTO) {
        return mapper.toGetRecipesIDTO(getRecipesRQRDTO);
    }

    @Override
    public RecipeRSRDTO toRecipeRSRDTO(@NonNull RecipeODTO recipeODTO) {
        return mapper.toRecipeRSRDTO(recipeODTO);
    }

    @Override
    public Page<RecipeSummaryRSRDTO> toRecipeSummaryRSRDTO(@NonNull Page<RecipeODTO> recipesODTO) {
        return mapper.toRecipeSummaryRSRDTO(recipesODTO);
    }

    @Override
    public GetRecipeIDTO toGetRecipeIDTO(@NonNull Long id, Long userId) {
        return mapper.toGetRecipeIDTO(id, userId);
    }

    @Override
    public RecipeInteractionIDTO toRecipeInteractionIDTO(@NonNull RecipeInteractionRQRDTO recipeInteractionRQRDTO, @NonNull Long userId) {
        return mapper.toRecipeInteractionsIDTO(recipeInteractionRQRDTO, userId);
    }

    @Override
    public RecipeInteractionRSRDTO toRecipeInteractionRSRDTO(@NonNull RecipeInteractionODTO recipeInteractionODTO) {
        return mapper.toRecipeInteractionsRSRDTO(recipeInteractionODTO);
    }

}
