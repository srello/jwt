package dev.srello.cocinillas.recipe.service.transformer;

import dev.srello.cocinillas.recipe.dto.RecipeInteractionIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeInteractionODTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.model.RecipeInteraction;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecipeServiceTransformer {
    Page<RecipeODTO> toRecipesODTO(@NonNull Page<Recipe> recipes, @NonNull List<RecipeInteraction> recipeInteractions);

    RecipeODTO toRecipeODTO(@NonNull Recipe recipe, @NotNull List<RecipeInteraction> recipeInteractions);

    RecipeInteraction toRecipeInteraction(@NonNull RecipeInteractionIDTO recipeInteractionIDTO);

    RecipeInteractionODTO toRecipeInteractionODTO(@NonNull RecipeInteraction recipeInteraction);
}
