package dev.srello.cocinillas.recipe.service.transformer;

import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.storage.service.StorageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecipeServiceTransformerImpl implements RecipeServiceTransformer {

    private final RecipeServiceMapper mapper;
    private final StorageService storageService;

    @Override
    public Page<RecipeODTO> toRecipesODTO(@NonNull Page<Recipe> recipes) {
        return recipes.map(this::toRecipeODTO);
    }

    @Override
    public RecipeODTO toRecipeODTO(@NonNull Recipe recipe) {
        List<URL> imageUrls = recipe.getImageKeys().stream()
                .map(storageService::getPresignedGetURL)
                .toList();
        return mapper.toRecipeODTO(recipe, imageUrls);
    }

}
