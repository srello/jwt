package dev.srello.cocinillas.recipe.service.transformer;

import dev.srello.cocinillas.recipe.dto.RecipeInteractionIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeInteractionODTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.model.RecipeInteraction;
import dev.srello.cocinillas.storage.service.StorageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

import static dev.srello.cocinillas.recipe.enums.RecipeInteractionType.LIKE;
import static dev.srello.cocinillas.recipe.enums.RecipeInteractionType.SAVE;

@Component
@RequiredArgsConstructor
public class RecipeServiceTransformerImpl implements RecipeServiceTransformer {

    private final RecipeServiceMapper mapper;
    private final StorageService storageService;

    @Override
    public Page<RecipeODTO> toRecipesODTO(@NonNull Page<Recipe> recipes, @NotNull List<RecipeInteraction> recipeInteractions) {
        return recipes.map(recipe -> toRecipeODTO(recipe, recipeInteractions));
    }

    @Override
    public List<RecipeODTO> toRecipesODTO(@NonNull List<Recipe> recipes, @NonNull List<RecipeInteraction> recipeInteractions) {
        return recipes.stream()
                .map(recipe -> toRecipeODTO(recipe, recipeInteractions))
                .toList();
    }

    @Override
    public RecipeODTO toRecipeODTO(@NonNull Recipe recipe, @NotNull List<RecipeInteraction> recipeInteractions) {
        List<URL> imageUrls = recipe.getImageKeys().stream()
                .map(storageService::getPresignedGetURL)
                .toList();
        List<RecipeInteraction> filteredInteractions = recipeInteractions.stream()
                .filter(recipeInteractionODTO -> recipeInteractionODTO.getRecipeId().equals(recipe.getId()))
                .toList();
        Boolean isLiked = filteredInteractions.stream().anyMatch(recipeInteractionODTO -> recipeInteractionODTO.getType().equals(LIKE));
        Boolean isSaved = filteredInteractions.stream().anyMatch(recipeInteractionODTO -> recipeInteractionODTO.getType().equals(SAVE));
        return mapper.toRecipeODTO(recipe, imageUrls, isLiked, isSaved);
    }

    @Override
    public RecipeInteraction toRecipeInteraction(@NonNull RecipeInteractionIDTO recipeInteractionIDTO) {
        return mapper.toRecipeInteraction(recipeInteractionIDTO);
    }

    @Override
    public RecipeInteractionODTO toRecipeInteractionODTO(@NonNull RecipeInteraction recipeInteraction) {
        return mapper.toRecipeInteractionODTO(recipeInteraction);
    }


}
