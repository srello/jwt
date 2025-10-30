package dev.srello.cocinillas.recipe.service;

import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import org.springframework.data.domain.Page;

public interface RecipeService {
    Page<RecipeODTO> getRecipesPaginated(GetRecipesIDTO getRecipesIDTO, PaginationIDTO pagination);

    RecipeODTO getRecipeById(GetRecipeIDTO getRecipeIDTO);

    RecipeInteractionODTO createRecipeInteraction(RecipeInteractionIDTO saveRecipeIDTO);

    RecipeInteractionODTO deleteRecipeInteraction(RecipeInteractionIDTO recipeInteractionIDTO);
}
