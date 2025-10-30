package dev.srello.cocinillas.recipe.repository;

import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.recipe.dto.GetRecipesIDTO;
import dev.srello.cocinillas.recipe.enums.RecipeVisibility;
import dev.srello.cocinillas.recipe.model.Ingredient;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.tags.model.Tag;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static dev.srello.cocinillas.recipe.enums.RecipeVisibility.*;
import static jakarta.persistence.criteria.JoinType.LEFT;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class RecipeSpecificationImpl implements RecipeSpecification {

    @Override
    public Specification<Recipe> buildRecipesPaginatedSpecification(GetRecipesIDTO getRecipesIDTO) {
        Specification<Recipe> nameSpecification = ofNullable(getRecipesIDTO.getName()).map(this::nameContains).orElse(null);
        Specification<Recipe> productsSpecification = ofNullable(getRecipesIDTO.getIngredients()).map(this::productsContainsAll).orElse(null);
        Specification<Recipe> tagsSpecification = ofNullable(getRecipesIDTO.getTags()).map(this::tagsContainsAll).orElse(null);
        Specification<Recipe> visibilitySpecification = this.isInVisibilityNotPrivate(getRecipesIDTO.getVisibility());

        return Stream.of(nameSpecification, productsSpecification, tagsSpecification, visibilitySpecification)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(visibilitySpecification);
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

    private Specification<Recipe> isInVisibilityNotPrivate(RecipeVisibility visibility) {

        return (recipeTable, query, criteriaBuilder) ->
        {
            In<Integer> inClause = criteriaBuilder.in(recipeTable.get("visibility"));
            if (nonNull(visibility) && !PRIVATE.equals(visibility))
                inClause.value(visibility.getVisibilityValue());
            else {
                inClause.value(PUBLIC.getVisibilityValue());
                inClause.value(OFFICIAL.getVisibilityValue());
            }

            return inClause;
        };
    }
}
