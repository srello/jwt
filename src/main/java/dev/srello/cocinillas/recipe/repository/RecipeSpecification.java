package dev.srello.cocinillas.recipe.repository;

import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import org.springframework.data.jpa.domain.Specification;

public interface RecipeSpecification {
    Specification<Recipe> buildRecipesPaginatedSpecification(RecipeIDTO recipeIDTO);
}
