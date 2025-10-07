package dev.srello.cocinillas.recipe.service.transformer;

import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import lombok.NonNull;
import org.springframework.data.domain.Page;

public interface RecipeServiceTransformer {
    Page<RecipeODTO> toRecipesODTO(@NonNull Page<Recipe> recipes);

    RecipeODTO toRecipeODTO(@NonNull Recipe recipes);
}
