package dev.srello.cocinillas.recipe.service.transformer;

import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeInteractionIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeInteractionODTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.model.RecipeInteraction;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;

public interface RecipeServiceTransformer {

    RecipeODTO toRecipeODTO(@NonNull Recipe recipe, @NotNull List<RecipeInteraction> recipeInteractions, @NonNull List<URL> imageUrls, Long userId);

    RecipeInteraction toRecipeInteraction(@NonNull RecipeInteractionIDTO recipeInteractionIDTO);

    RecipeInteractionODTO toRecipeInteractionODTO(@NonNull RecipeInteraction recipeInteraction);

    Recipe toRecipe(@NonNull RecipeIDTO recipeIDTO);
}
