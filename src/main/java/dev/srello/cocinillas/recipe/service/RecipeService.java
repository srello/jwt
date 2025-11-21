package dev.srello.cocinillas.recipe.service;

import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeService {
    Page<RecipeODTO> getRecipesPaginated(GetRecipesIDTO getRecipesIDTO, PaginationIDTO pagination);

    Page<RecipeODTO> getUserRecipesPaginated(GetRecipesIDTO id, PaginationIDTO paginationIDTO);

    RecipeODTO getRecipeById(GetRecipeIDTO getRecipeIDTO);

    List<RecipeODTO> getRecipesById(GetRecipesByIdIDTO getRecipesByIdIDTO);

    RecipeInteractionODTO createRecipeInteraction(RecipeInteractionIDTO saveRecipeIDTO);

    RecipeInteractionODTO deleteRecipeInteraction(RecipeInteractionIDTO recipeInteractionIDTO);

    RecipeODTO deleteRecipeById(DeleteRecipeIDTO deleteRecipeIDTO);

    RecipeODTO createRecipe(RecipeIDTO recipeIDTO);
}
