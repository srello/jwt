package dev.srello.cocinillas.recipe.service;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.repository.RecipeRepository;
import dev.srello.cocinillas.recipe.repository.RecipeSpecification;
import dev.srello.cocinillas.recipe.service.transformer.RecipeServiceTransformer;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.RECIPE_NOT_FOUND_CODE;
import static dev.srello.cocinillas.core.messages.Messages.Error.RECIPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository repository;
    private final RecipeServiceTransformer transformer;
    private final RecipeSpecification specification;

    @Override
    public Page<RecipeODTO> getRecipesPaginated(RecipeIDTO recipeIDTO, PaginationIDTO pagination) {
        Specification<Recipe> recipeSpecification = specification.buildRecipesPaginatedSpecification(recipeIDTO);
        Page<Recipe> recipes = repository.findAll(recipeSpecification, pagination.getPageRequest());
        return transformer.toRecipesODTO(recipes);
    }

    @Override
    public RecipeODTO getRecipeById(Long id) {
        Recipe recipe = repository.findById(id).orElseThrow(() -> new RequestException(HttpStatus.NOT_FOUND, RECIPE_NOT_FOUND.formatted(id), RECIPE_NOT_FOUND_CODE));
        return transformer.toRecipeODTO(recipe);
    }
}
