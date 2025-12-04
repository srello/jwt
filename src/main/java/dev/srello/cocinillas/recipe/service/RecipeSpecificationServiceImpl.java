package dev.srello.cocinillas.recipe.service;

import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.recipe.dto.GetRecipesIDTO;
import dev.srello.cocinillas.recipe.model.Ingredient;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.model.RecipeInteraction;
import dev.srello.cocinillas.shared.enums.Visibility;
import dev.srello.cocinillas.tags.model.Tag;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static dev.srello.cocinillas.shared.enums.InteractionType.SAVE;
import static dev.srello.cocinillas.shared.enums.Visibility.*;
import static jakarta.persistence.criteria.JoinType.LEFT;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class RecipeSpecificationServiceImpl implements RecipeSpecificationService {

    @Override
    public Specification<Recipe> buildRecipesPaginatedSpecification(GetRecipesIDTO getRecipesIDTO) {
        Specification<Recipe> nameSpecification = ofNullable(getRecipesIDTO.getName()).map(this::nameContains).orElse(null);
        Specification<Recipe> productsSpecification = ofNullable(getRecipesIDTO.getIngredients()).map(this::productsContainsAll).orElse(null);
        Specification<Recipe> tagsSpecification = ofNullable(getRecipesIDTO.getTags()).map(this::tagsContainsAll).orElse(null);
        Specification<Recipe> visibilitySpecification = visibilitySpecification(getRecipesIDTO.getVisibility(), getRecipesIDTO.getUserId());

        return Stream.of(nameSpecification, productsSpecification, tagsSpecification, visibilitySpecification)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(visibilitySpecification);
    }

    @Override
    public Specification<Recipe> buildUserRecipesPaginatedSpecification(GetRecipesIDTO getRecipesIDTO) {
        Specification<Recipe> nameSpecification = ofNullable(getRecipesIDTO.getName()).map(this::nameContains).orElse(null);
        Specification<Recipe> productsSpecification = ofNullable(getRecipesIDTO.getIngredients()).map(this::productsContainsAll).orElse(null);
        Specification<Recipe> tagsSpecification = ofNullable(getRecipesIDTO.getTags()).map(this::tagsContainsAll).orElse(null);
        Specification<Recipe> userRecipeSpecification = isUserRecipe(getRecipesIDTO.getUserId());

        return Stream.of(nameSpecification, productsSpecification, tagsSpecification, userRecipeSpecification)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(userRecipeSpecification);
    }

    private Specification<Recipe> isUserRecipe(Long userId) {
        return (recipeTable, query, cb) -> {
            Predicate isAuthor = cb.equal(recipeTable.get("author").get("id"), userId);
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<RecipeInteraction> interaction = subquery.from(RecipeInteraction.class);
            subquery.select(interaction.get("recipeId"))
                    .where(
                            cb.equal(interaction.get("userId"), userId),
                            cb.equal(interaction.get("type"), SAVE)
                    );
            Predicate isSaved = recipeTable.get("id").in(subquery);
            return cb.or(isAuthor, isSaved);
        };
    }

    private Specification<Recipe> nameContains(String filterString) {
        return (recipeTable, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(recipeTable.get("name")), "%" + filterString.toLowerCase() + "%");
    }

    private Specification<Recipe> tagsContainsAll(List<String> tags) {
        return (recipeTable, query, cb) -> {
            query.distinct(true);

            Join<Recipe, Tag> tagsJoin = recipeTable.join("tags", LEFT);

            List<String> lowerCaseTags = tags.stream()
                    .map(String::toLowerCase)
                    .toList();

            Predicate inTags = cb.lower(tagsJoin.get("name")).in(lowerCaseTags);
            query.where(inTags);
            query.groupBy(recipeTable.get("id"));
            Predicate havingCount = cb.equal(cb.countDistinct(tagsJoin.get("name")), lowerCaseTags.size());
            query.having(havingCount);
            return query.getRestriction();
        };
    }

    private Specification<Recipe> productsContainsAll(List<String> ingredients) {
        return (recipeTable, query, cb) -> {
            query.distinct(true);

            Join<Recipe, Ingredient> ingredientsJoin = recipeTable.join("ingredients", LEFT);
            Join<Ingredient, Product> productsJoin = ingredientsJoin.join("product", LEFT);

            List<String> lowerCaseIngredients = ingredients.stream()
                    .map(String::toLowerCase)
                    .toList();

            Predicate inProducts = cb.lower(productsJoin.get("name")).in(lowerCaseIngredients);
            query.where(inProducts);
            query.groupBy(recipeTable.get("id"));
            Predicate havingCount = cb.equal(cb.countDistinct(productsJoin.get("name")), lowerCaseIngredients.size());
            query.having(havingCount);
            return query.getRestriction();
        };
    }

    private Specification<Recipe> visibilitySpecification(Visibility visibility, Long userId) {
        return (recipeTable, query, criteriaBuilder) ->
        {
            Path<Visibility> recipeVisibilityPath = recipeTable.get("visibility");
            Path<Long> authorId = recipeTable.get("author").get("id");

            return switch (visibility) {
                case PRIVATE -> criteriaBuilder.and(
                        criteriaBuilder.equal(recipeVisibilityPath, visibility),
                        criteriaBuilder.equal(authorId, userId)
                );
                case OFFICIAL, PUBLIC -> criteriaBuilder.equal(recipeVisibilityPath, visibility);
                case null -> criteriaBuilder.or(
                        criteriaBuilder.equal(recipeVisibilityPath, OFFICIAL),
                        criteriaBuilder.equal(recipeVisibilityPath, PUBLIC),
                        criteriaBuilder.and(
                                criteriaBuilder.equal(recipeVisibilityPath, PRIVATE),
                                criteriaBuilder.equal(authorId, userId)
                        )
                );
            };
        };
    }
}
