package dev.srello.cocinillas.recipe.service.transformer;

import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeInteractionIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeInteractionODTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.model.RecipeInteraction;
import dev.srello.cocinillas.tags.model.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

import static dev.srello.cocinillas.shared.enums.InteractionType.LIKE;
import static dev.srello.cocinillas.shared.enums.InteractionType.SAVE;

@Component
@RequiredArgsConstructor
public class RecipeServiceTransformerImpl implements RecipeServiceTransformer {

    private final RecipeServiceMapper mapper;

    @Override
    public RecipeODTO toRecipeODTO(@NonNull Recipe recipe, @NonNull List<RecipeInteraction> recipeInteractions, @NonNull List<URL> imageUrls, Long userId) {

        List<RecipeInteraction> filteredInteractions = recipeInteractions.stream()
                .filter(recipeInteractionODTO -> recipeInteractionODTO.getRecipeId().equals(recipe.getId()))
                .toList();
        Boolean isLiked = filteredInteractions.stream().anyMatch(recipeInteractionODTO -> recipeInteractionODTO.getType().equals(LIKE));
        Boolean isSaved = filteredInteractions.stream().anyMatch(recipeInteractionODTO -> recipeInteractionODTO.getType().equals(SAVE));

        return mapper.toRecipeODTO(recipe, imageUrls, isLiked, isSaved, recipe.getAuthor(), userId);
    }

    @Override
    public RecipeInteraction toRecipeInteraction(@NonNull RecipeInteractionIDTO recipeInteractionIDTO) {
        return mapper.toRecipeInteraction(recipeInteractionIDTO);
    }

    @Override
    public RecipeInteractionODTO toRecipeInteractionODTO(@NonNull RecipeInteraction recipeInteraction) {
        return mapper.toRecipeInteractionODTO(recipeInteraction);
    }

    @Override
    public Recipe toRecipe(@NonNull RecipeIDTO recipeIDTO, @NotNull List<Product> products, List<Tag> tags) {
        return mapper.toRecipe(recipeIDTO, tags, products);
    }


}
