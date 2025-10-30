package dev.srello.cocinillas.recipe.repository;

import dev.srello.cocinillas.recipe.enums.RecipeInteractionType;
import dev.srello.cocinillas.recipe.model.RecipeInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeInteractionRepository extends JpaRepository<RecipeInteraction, Long> {
    Optional<RecipeInteraction> findByUserIdAndRecipeIdAndType(Long userId, Long recipeId, RecipeInteractionType type);

    List<RecipeInteraction> findAllByUserIdAndRecipeIdIn(Long userId, List<Long> recipeIds);
}
