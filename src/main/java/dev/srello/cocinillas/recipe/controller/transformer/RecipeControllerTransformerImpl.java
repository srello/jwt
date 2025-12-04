package dev.srello.cocinillas.recipe.controller.transformer;

import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.rdto.*;
import dev.srello.cocinillas.user.dto.UserODTO;
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
    public GetRecipesIDTO toGetRecipesIDTO(@NonNull GetUserRecipesRQRDTO getUserRecipesRQRDTO, @NonNull Long userId) {
        return mapper.toGetRecipesIDTO(getUserRecipesRQRDTO, userId);
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
    public DeleteRecipeIDTO toDeleteRecipeIDTO(@NonNull Long recipeId, @NonNull UserODTO user) {
        return mapper.toDeleteRecipeIDTO(recipeId, user);
    }

    @Override
    public RecipeInteractionIDTO toRecipeInteractionIDTO(@NonNull RecipeInteractionRQRDTO recipeInteractionRQRDTO, @NonNull Long userId) {
        return mapper.toRecipeInteractionsIDTO(recipeInteractionRQRDTO, userId);
    }

    @Override
    public RecipeInteractionRSRDTO toRecipeInteractionRSRDTO(@NonNull RecipeInteractionODTO recipeInteractionODTO) {
        return mapper.toRecipeInteractionsRSRDTO(recipeInteractionODTO);
    }

    @Override
    public RecipeIDTO toRecipeIDTO(@NonNull RecipeRQRDTO recipeRQRDTO, @NonNull UserODTO author) {
        return mapper.toRecipeIDTO(recipeRQRDTO, author);
    }

    @Override
    public EditRecipeIDTO toEditRecipeIDTO(@NonNull Long id, @NonNull RecipeRQRDTO recipeRQRDTO, @NonNull UserODTO author) {
        RecipeIDTO recipeIDTO = mapper.toRecipeIDTO(recipeRQRDTO, author);
        return mapper.toEditRecipeIDTO(id, recipeIDTO);
    }
}
