package dev.srello.cocinillas.recipe.service.transformer;

import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.model.RecipeInteraction;
import dev.srello.cocinillas.storage.service.StorageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

import static dev.srello.cocinillas.recipe.enums.RecipeInteractionType.LIKE;
import static dev.srello.cocinillas.recipe.enums.RecipeInteractionType.SAVE;
import static java.util.Objects.nonNull;

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

        RecipeAuthorODTO author = buildRecipeAuthor(recipe, userId);
        return mapper.toRecipeODTO(recipe, imageUrls, isLiked, isSaved, author);
    }

    private RecipeAuthorODTO buildRecipeAuthor(Recipe recipe, Long userId) {
        Boolean isUserAuthor = nonNull(userId) && userId.equals(recipe.getAuthor().getId());

        return mapper.toRecipeAuthorODTO(recipe.getAuthor().getUsername(), isUserAuthor);
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
    public Recipe toRecipe(@NonNull RecipeIDTO recipeIDTO) {
        return mapper.toRecipe(recipeIDTO);
    }


}
