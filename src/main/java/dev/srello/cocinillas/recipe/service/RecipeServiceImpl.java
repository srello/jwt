package dev.srello.cocinillas.recipe.service;

import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.repository.RecipeRepository;
import dev.srello.cocinillas.recipe.service.transformer.RecipeServiceTransformer;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository repository;
    private final RecipeServiceTransformer transformer;

    @Override
    public Page<RecipeODTO> getRecipesPaginated(RecipeIDTO recipeIDTO, PaginationIDTO pagination) {
        Page<Recipe> recipes = repository.findAll(pagination.getPageRequest());
        return transformer.toRecipesODTO(recipes);
    }
}
