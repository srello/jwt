package dev.srello.cocinillas.recipe.repository;

import dev.srello.cocinillas.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
