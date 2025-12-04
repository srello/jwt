package dev.srello.cocinillas.recipe.controller.transformer;

import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.rdto.*;
import dev.srello.cocinillas.user.dto.UserODTO;
import lombok.NonNull;
import org.springframework.data.domain.Page;

public interface RecipeControllerTransformer {
    GetRecipesIDTO toGetRecipesIDTO(@NonNull GetRecipesRQRDTO getRecipesRQRDTO);

    GetRecipesIDTO toGetRecipesIDTO(@NonNull GetUserRecipesRQRDTO getUserRecipesRQRDTO, @NonNull Long userId);

    RecipeRSRDTO toRecipeRSRDTO(@NonNull RecipeODTO recipeODTO);

    Page<RecipeSummaryRSRDTO> toRecipeSummaryRSRDTO(@NonNull Page<RecipeODTO> recipesODTO);

    GetRecipeIDTO toGetRecipeIDTO(@NonNull Long id, Long userId);

    DeleteRecipeIDTO toDeleteRecipeIDTO(@NonNull Long recipeId, @NonNull UserODTO user);

    RecipeInteractionIDTO toRecipeInteractionIDTO(@NonNull RecipeInteractionRQRDTO recipeInteractionRQRDTO, @NonNull Long userId);

    RecipeInteractionRSRDTO toRecipeInteractionRSRDTO(@NonNull RecipeInteractionODTO recipeInteractionODTO);

    RecipeIDTO toRecipeIDTO(@NonNull RecipeRQRDTO recipeRQRDTO, @NonNull UserODTO author);

    EditRecipeIDTO toEditRecipeIDTO(@NonNull Long id, @NonNull RecipeRQRDTO recipeRQRDTO, @NonNull UserODTO author);
}
