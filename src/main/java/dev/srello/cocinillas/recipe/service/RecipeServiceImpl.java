package dev.srello.cocinillas.recipe.service;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.model.RecipeInteraction;
import dev.srello.cocinillas.recipe.repository.RecipeInteractionRepository;
import dev.srello.cocinillas.recipe.repository.RecipeRepository;
import dev.srello.cocinillas.recipe.repository.RecipeSpecification;
import dev.srello.cocinillas.recipe.service.transformer.RecipeServiceTransformer;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.*;
import static dev.srello.cocinillas.core.messages.Messages.Error.*;
import static java.util.Collections.emptyList;
import static java.util.List.of;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeServiceTransformer transformer;
    private final RecipeSpecification specification;
    private final RecipeRepository repository;
    private final RecipeInteractionRepository recipeInteractionRepository;

    @Override
    public Page<RecipeODTO> getRecipesPaginated(GetRecipesIDTO getRecipesIDTO, PaginationIDTO pagination) {
        Specification<Recipe> recipeSpecification = specification.buildRecipesPaginatedSpecification(getRecipesIDTO);
        Page<Recipe> recipes = repository.findAll(recipeSpecification, pagination.getPageRequest());

        List<RecipeInteraction> recipeInteractions = getRecipeInteractionsList(recipes.stream().toList(), getRecipesIDTO.getUserId());

        return transformer.toRecipesODTO(recipes, recipeInteractions);
    }

    @Override
    public RecipeODTO getRecipeById(GetRecipeIDTO getRecipeIDTO) {
        Recipe recipe = findRecipeById(getRecipeIDTO.getId());
        List<RecipeInteraction> recipeInteractions = getRecipeInteractionsList(of(recipe), getRecipeIDTO.getUserId());
        return transformer.toRecipeODTO(recipe, recipeInteractions);
    }

    @Override
    public RecipeInteractionODTO createRecipeInteraction(RecipeInteractionIDTO saveRecipeIDTO) {
        RecipeInteraction recipeInteraction = transformer.toRecipeInteraction(saveRecipeIDTO);
        Long recipeId = recipeInteraction.getRecipeId();
        recipeInteractionRepository.findByUserIdAndRecipeIdAndType(recipeInteraction.getUserId(), recipeId, recipeInteraction.getType())
                .ifPresent(ignored -> {
                    throw new RequestException(BAD_REQUEST, RECIPE_INTERACTION_ALREADY_EXISTS, RECIPE_INTERACTION_ALREADY_EXISTS_CODE);
                });
        RecipeInteraction savedRecipeInteraction = recipeInteractionRepository.saveAndFlush(recipeInteraction);
        changeRecipeLikes(recipeId, 1);
        return transformer.toRecipeInteractionODTO(savedRecipeInteraction);
    }

    @Override
    public RecipeInteractionODTO deleteRecipeInteraction(RecipeInteractionIDTO recipeInteractionIDTO) {
        Long recipeId = recipeInteractionIDTO.getRecipeId();
        RecipeInteraction recipeInteraction = recipeInteractionRepository.findByUserIdAndRecipeIdAndType(recipeInteractionIDTO.getUserId(), recipeId, recipeInteractionIDTO.getType())
                .orElseThrow(() -> new RequestException(NOT_FOUND, RECIPE_INTERACTION_NOT_FOUND, RECIPE_INTERACTION_NOT_FOUND_CODE));

        recipeInteractionRepository.delete(recipeInteraction);
        changeRecipeLikes(recipeId, -1);
        return transformer.toRecipeInteractionODTO(recipeInteraction);
    }

    private Recipe findRecipeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RequestException(HttpStatus.NOT_FOUND, RECIPE_NOT_FOUND.formatted(id), RECIPE_NOT_FOUND_CODE));
    }

    private void changeRecipeLikes(Long recipeId, Integer likesToAdd) {
        Recipe recipe = findRecipeById(recipeId);
        recipe.setLikes(recipe.getLikes() + likesToAdd);
        repository.saveAndFlush(recipe);
    }

    private List<RecipeInteraction> getRecipeInteractionsList(List<Recipe> recipes, Long userId) {
        if (isNull(userId))
            return emptyList();

        List<Long> recipeIds = recipes.stream().map(Recipe::getId).toList();
        return recipeInteractionRepository.findAllByUserIdAndRecipeIdIn(userId, recipeIds);
    }
}
