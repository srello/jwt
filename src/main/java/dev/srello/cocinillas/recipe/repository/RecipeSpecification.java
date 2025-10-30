package dev.srello.cocinillas.recipe.repository;

import dev.srello.cocinillas.recipe.dto.GetRecipesIDTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import org.springframework.data.jpa.domain.Specification;

public interface RecipeSpecification {
    Specification<Recipe> buildRecipesPaginatedSpecification(GetRecipesIDTO getRecipesIDTO);
}
