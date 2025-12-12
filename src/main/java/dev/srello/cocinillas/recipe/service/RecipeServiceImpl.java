package dev.srello.cocinillas.recipe.service;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.product.adapter.ProductServiceAdapter;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.model.Instruction;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.model.RecipeInteraction;
import dev.srello.cocinillas.recipe.repository.RecipeInteractionRepository;
import dev.srello.cocinillas.recipe.repository.RecipeRepository;
import dev.srello.cocinillas.recipe.service.transformer.RecipeServiceTransformer;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.storage.service.StorageService;
import dev.srello.cocinillas.tags.adapter.TagServiceAdapter;
import dev.srello.cocinillas.tags.model.Tag;
import dev.srello.cocinillas.user.dto.UserODTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Objects;

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.*;
import static dev.srello.cocinillas.core.messages.Messages.Error.*;
import static dev.srello.cocinillas.shared.enums.InteractionType.LIKE;
import static dev.srello.cocinillas.user.enums.Role.ADMIN;
import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;
import static java.util.List.of;
import static java.util.Objects.isNull;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeServiceTransformer transformer;
    private final RecipeSpecificationService specification;
    private final RecipeRepository repository;
    private final RecipeInteractionRepository recipeInteractionRepository;
    private final StorageService storageService;
    private final ProductServiceAdapter productServiceAdapter;
    private final TagServiceAdapter tagServiceAdapter;

    @Override
    public Page<RecipeODTO> getRecipesPaginated(GetRecipesIDTO getRecipesIDTO, PaginationIDTO pagination) {
        Specification<Recipe> recipeSpecification = specification.buildRecipesPaginatedSpecification(getRecipesIDTO);
        Page<Recipe> recipes = repository.findAll(recipeSpecification, pagination.getPageRequest());

        Long userId = getRecipesIDTO.getUserId();
        List<RecipeInteraction> recipeInteractions = getRecipeInteractionsList(recipes.stream().toList(), userId);

        return recipes.map(recipe -> transformer.toRecipeODTO(recipe, recipeInteractions, getImageUrls(recipe.getImageKeys()), userId));
    }

    @Override
    public Page<RecipeODTO> getUserRecipesPaginated(GetRecipesIDTO getRecipesIDTO, PaginationIDTO paginationIDTO) {
        Specification<Recipe> recipeSpecification = specification.buildUserRecipesPaginatedSpecification(getRecipesIDTO);
        Page<Recipe> recipes = repository.findAll(recipeSpecification, paginationIDTO.getPageRequest());
        Long userId = getRecipesIDTO.getUserId();
        List<RecipeInteraction> recipeInteractions = getRecipeInteractionsList(recipes.stream().toList(), userId);
        return recipes.map(recipe -> transformer.toRecipeODTO(recipe, recipeInteractions, getImageUrls(recipe.getImageKeys()), userId));
    }

    @Override
    public RecipeODTO getRecipeById(GetRecipeIDTO getRecipeIDTO) {
        Recipe recipe = findRecipeById(getRecipeIDTO.getId());
        Long userId = getRecipeIDTO.getUserId();
        List<RecipeInteraction> recipeInteractions = getRecipeInteractionsList(of(recipe), userId);
        return transformer.toRecipeODTO(recipe, recipeInteractions, getImageUrls(recipe.getImageKeys()), userId);
    }

    @Override
    public List<RecipeODTO> getRecipesById(GetRecipesByIdIDTO getRecipesByIdIDTO) {
        List<Recipe> recipes = getModelRecipesByIds(getRecipesByIdIDTO.getRecipeIds());
        Long userId = getRecipesByIdIDTO.getUserId();
        List<RecipeInteraction> recipeInteractions = getRecipeInteractionsList(recipes, userId);
        return recipes.stream()
                .map(recipe -> transformer.toRecipeODTO(recipe, recipeInteractions, getImageUrls(recipe.getImageKeys()), userId))
                .toList();
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
        if (LIKE.equals(savedRecipeInteraction.getType()))
            changeRecipeLikes(recipeId, 1);
        return transformer.toRecipeInteractionODTO(savedRecipeInteraction);
    }

    @Override
    public RecipeInteractionODTO deleteRecipeInteraction(RecipeInteractionIDTO recipeInteractionIDTO) {
        RecipeInteraction recipeInteraction = recipeInteractionRepository
                .findByUserIdAndRecipeIdAndType(recipeInteractionIDTO.getUserId(), recipeInteractionIDTO.getRecipeId(), recipeInteractionIDTO.getType())
                .orElseThrow(() -> new RequestException(NOT_FOUND, RECIPE_INTERACTION_NOT_FOUND, RECIPE_INTERACTION_NOT_FOUND_CODE));

        recipeInteractionRepository.delete(recipeInteraction);
        if (LIKE.equals(recipeInteraction.getType()))
            changeRecipeLikes(recipeInteraction.getRecipeId(), -1);

        return transformer.toRecipeInteractionODTO(recipeInteraction);
    }

    @Override
    public RecipeODTO deleteRecipeById(DeleteRecipeIDTO deleteRecipeIDTO) {
        Recipe recipe = findRecipeById(deleteRecipeIDTO.getRecipeId());
        UserODTO user = deleteRecipeIDTO.getUser();
        if (!ADMIN.equals(user.getRole()) && !recipe.getAuthor().getId().equals(user.getId()))
            throw new RequestException(BAD_REQUEST, RESOURCE_DELETION_NOT_ALLOWED, RESOURCE_DELETION_NOT_ALLOWED_CODE);

        List<RecipeInteraction> recipeInteractions = getRecipeInteractionsList(of(recipe), user.getId());
        recipeInteractionRepository.deleteAll(recipeInteractions);
        storageService.deleteObjects(concat(recipe.getImageKeys().stream(), recipe.getInstructions().stream().map(Instruction::getImageKey)).toList());
        repository.delete(recipe);

        return transformer.toRecipeODTO(recipe, recipeInteractions, emptyList(), user.getId());
    }

    @Override
    public RecipeODTO createRecipe(RecipeIDTO recipeIDTO) {
        Recipe recipe = getRecipeFromIDTO(recipeIDTO);
        recipe.setCreationDate(now());
        Recipe savedRecipe = repository.saveAndFlush(recipe);
        List<URL> imageUrls = recipeIDTO.getImages().stream()
                .map(image -> storageService.getPresignedPutURL(image.getKey(), image.getContentType()))
                .toList();
        return transformer.toRecipeODTO(savedRecipe, emptyList(), imageUrls, savedRecipe.getAuthor().getId());
    }

    @Override
    public RecipeODTO editRecipeById(EditRecipeIDTO editRecipeIDTO) {
        Recipe recipe = findRecipeById(editRecipeIDTO.getId());
        RecipeIDTO recipeIDTO = editRecipeIDTO.getRecipeIDTO();
        if (!recipe.getAuthor().getId().equals(recipeIDTO.getAuthor().getId())) {
            throw new RequestException(UNAUTHORIZED, RESOURCE_MODIFICATION_NOT_ALLOWED, RESOURCE_MODIFICATION_NOT_ALLOWED_CODE);
        }

        Recipe updatedRecipe = getRecipeFromIDTO(recipeIDTO);
        updatedRecipe.setId(recipe.getId());
        updatedRecipe.setLastUpdateDate(now());
        Recipe savedRecipe = repository.saveAndFlush(recipe);
        List<RecipeImageIDTO> imagesIDTO = recipeIDTO.getImages();
        List<URL> imageUrls = imagesIDTO.stream()
                .filter(Objects::nonNull)
                .map(image -> storageService.getPresignedPutURL(image.getKey(), image.getContentType()))
                .toList();
        List<String> keysToDelete = range(0, imagesIDTO.size())
                .filter(i -> isNull(imagesIDTO.get(i)))
                .mapToObj(i -> recipe.getImageKeys().get(i))
                .toList();
        storageService.deleteObjects(keysToDelete);
        return transformer.toRecipeODTO(savedRecipe, emptyList(), imageUrls, savedRecipe.getAuthor().getId());
    }

    @Override
    public List<Recipe> getModelRecipesByIds(List<Long> recipeIds) {
        return repository.findAllById(recipeIds);
    }

    private Recipe getRecipeFromIDTO(RecipeIDTO recipeIDTO) {
        List<Product> products = productServiceAdapter.getProductsFromRecipeIDTO(recipeIDTO);
        List<Tag> tags = tagServiceAdapter.getTagsFromRecipeIDTO(recipeIDTO);
        return transformer.toRecipe(recipeIDTO, products, tags);
    }

    private Recipe findRecipeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RequestException(NOT_FOUND, RECIPE_NOT_FOUND.formatted(id), RECIPE_NOT_FOUND_CODE));
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

    private List<URL> getImageUrls(List<String> imageKeys) {
        return imageKeys.stream()
                .map(storageService::getPresignedGetURL)
                .toList();
    }
}

