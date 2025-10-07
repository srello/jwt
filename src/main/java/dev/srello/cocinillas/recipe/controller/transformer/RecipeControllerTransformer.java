package dev.srello.cocinillas.recipe.controller.transformer;

import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.rdto.RecipeRQRDTO;
import dev.srello.cocinillas.recipe.rdto.RecipeRSRDTO;
import dev.srello.cocinillas.recipe.rdto.RecipeSummaryRSRDTO;
import lombok.NonNull;
import org.springframework.data.domain.Page;

public interface RecipeControllerTransformer {
    RecipeIDTO toRecipeIDTO(@NonNull RecipeRQRDTO recipeRQRDTO);

    Page<RecipeRSRDTO> toRecipesRSRDTO(@NonNull Page<RecipeODTO> recipesODTO);

    RecipeRSRDTO toRecipeRSRDTO(@NonNull RecipeODTO recipeODTO);

    Page<RecipeSummaryRSRDTO> toRecipeSummaryRSRDTO(@NonNull Page<RecipeODTO> recipesODTO);
}
