package dev.srello.cocinillas.recipe.service;

import dev.srello.cocinillas.recipe.dto.GetRecipesIDTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import org.springframework.data.jpa.domain.Specification;

public interface RecipeSpecificationService {
    Specification<Recipe> buildRecipesPaginatedSpecification(GetRecipesIDTO getRecipesIDTO);

    Specification<Recipe> buildUserRecipesPaginatedSpecification(Long userId);
}
