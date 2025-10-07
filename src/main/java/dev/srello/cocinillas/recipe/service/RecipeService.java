package dev.srello.cocinillas.recipe.service;

import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import org.springframework.data.domain.Page;

public interface RecipeService {
    Page<RecipeODTO> getRecipesPaginated(RecipeIDTO recipeIDTO, PaginationIDTO pagination);

    RecipeODTO getRecipeById(Long id);
}
