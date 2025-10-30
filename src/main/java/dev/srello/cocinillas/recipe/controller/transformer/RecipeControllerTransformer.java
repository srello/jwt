package dev.srello.cocinillas.recipe.controller.transformer;

import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.rdto.*;
import lombok.NonNull;
import org.springframework.data.domain.Page;

public interface RecipeControllerTransformer {
    GetRecipesIDTO toGetRecipesIDTO(@NonNull GetRecipesRQRDTO getRecipesRQRDTO);

    RecipeRSRDTO toRecipeRSRDTO(@NonNull RecipeODTO recipeODTO);

    Page<RecipeSummaryRSRDTO> toRecipeSummaryRSRDTO(@NonNull Page<RecipeODTO> recipesODTO);

    GetRecipeIDTO toGetRecipeIDTO(@NonNull Long id, Long userId);

    RecipeInteractionIDTO toRecipeInteractionIDTO(@NonNull RecipeInteractionRQRDTO recipeInteractionRQRDTO, @NonNull Long userId);

    RecipeInteractionRSRDTO toRecipeInteractionRSRDTO(@NonNull RecipeInteractionODTO recipeInteractionODTO);
}
