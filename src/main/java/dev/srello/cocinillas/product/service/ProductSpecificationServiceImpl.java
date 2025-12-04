package dev.srello.cocinillas.product.service;

import dev.srello.cocinillas.allergen.model.Allergen;
import dev.srello.cocinillas.core.BaseSpecificationService;
import dev.srello.cocinillas.product.dto.GetProductsIDTO;
import dev.srello.cocinillas.product.enums.ProductCategory;
import dev.srello.cocinillas.product.enums.ProductCategory.ProductGroup;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.shared.enums.Visibility;
import dev.srello.cocinillas.tags.model.Tag;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static dev.srello.cocinillas.shared.enums.Visibility.*;
import static jakarta.persistence.criteria.JoinType.LEFT;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ProductSpecificationServiceImpl extends BaseSpecificationService<Product> implements ProductSpecificationService {
    @Override
    public Specification<Product> buildGetProductsPaginatedSpecification(GetProductsIDTO getProductsIDTO) {
        return fieldContains(getProductsIDTO.getName(), "name")
                .and(tagsContainsAll(getProductsIDTO.getTags()))
                .and(allergenNotIn(getProductsIDTO.getAllergens()))
                .and(belongsToGroups(getProductsIDTO.getGroups()))
                .and(belongsToCategories(getProductsIDTO.getCategories()))
                .and(visibilitySpecification(getProductsIDTO.getVisibility(), getProductsIDTO.getUserId()));
    }

    @Override
    public Specification<Product> buildGetUserProductsPaginatedSpecification(GetProductsIDTO getProductsIDTO) {
        return fieldContains(getProductsIDTO.getName(), "name")
                .and(tagsContainsAll(getProductsIDTO.getTags()))
                .and(allergenNotIn(getProductsIDTO.getAllergens()))
                .and(belongsToGroups(getProductsIDTO.getGroups()))
                .and(belongsToCategories(getProductsIDTO.getCategories()))
                .and(userProducts(getProductsIDTO.getUserId()));
    }

    private Specification<Product> belongsToGroups(List<ProductGroup> groups) {
        if (isNull(groups) || groups.isEmpty())
            return emptySpecification();
        List<ProductCategory> categories = groups.stream()
                .map(ProductCategory::getByGroup)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
        return belongsToCategories(categories);
    }

    private Specification<Product> belongsToCategories(List<ProductCategory> categories) {
        if (isNull(categories) || categories.isEmpty())
            return emptySpecification();
        return (productTable, query, cb) -> query.where(productTable.get("productCategory").in(categories)).getRestriction();
    }

    private Specification<Product> tagsContainsAll(List<String> tags) {
        if (isNull(tags) || tags.isEmpty())
            return emptySpecification();
        return (productTable, query, cb) -> {
            query.distinct(true);
            Join<Product, Tag> tagsJoin = productTable.join("tags", LEFT);

            List<String> lowerCaseTags = tags.stream()
                    .map(String::toLowerCase)
                    .toList();

            Predicate inTags = cb.lower(tagsJoin.get("name")).in(lowerCaseTags);
            query.where(inTags);
            query.groupBy(productTable.get("id"));
            Predicate havingCount = cb.equal(cb.countDistinct(tagsJoin.get("name")), lowerCaseTags.size());
            query.having(havingCount);
            return query.getRestriction();
        };
    }

    private Specification<Product> allergenNotIn(List<String> allergens) {
        if (isNull(allergens) || allergens.isEmpty())
            return emptySpecification();

        return (productTable, query, cb) -> {
            List<String> lowerCaseAllergens = allergens.stream()
                    .filter(Objects::nonNull)
                    .map(String::toLowerCase)
                    .toList();

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Product> subroot = subquery.from(Product.class);
            Join<Product, Allergen> allergensJoin = subroot.join("allergens", LEFT);

            subquery.select(subroot.get("id"))
                    .where(
                            cb.and(
                                    cb.equal(subroot.get("id"), productTable.get("id")),
                                    cb.lower(allergensJoin.get("name")).in(lowerCaseAllergens)
                            )
                    );

            return cb.not(cb.exists(subquery));
        };
    }

    private Specification<Product> visibilitySpecification(Visibility visibility, Long userId) {
        return (productTable, query, criteriaBuilder) ->
        {
            Path<Visibility> productVisibilityPath = productTable.get("visibility");
            Path<Long> authorId = productTable.get("author").get("id");

            return switch (visibility) {
                case PRIVATE -> criteriaBuilder.and(
                        criteriaBuilder.equal(productVisibilityPath, visibility),
                        criteriaBuilder.equal(authorId, userId)
                );
                case OFFICIAL, PUBLIC -> criteriaBuilder.equal(productVisibilityPath, visibility);
                case null -> criteriaBuilder.or(
                        criteriaBuilder.equal(productVisibilityPath, OFFICIAL),
                        criteriaBuilder.equal(productVisibilityPath, PUBLIC),
                        criteriaBuilder.and(
                                criteriaBuilder.equal(productVisibilityPath, PRIVATE),
                                criteriaBuilder.equal(authorId, userId)
                        )
                );
            };
        };
    }

    private Specification<Product> userProducts(Long userId) {
        return (productTable, query, criteriaBuilder) ->
                criteriaBuilder.equal(productTable.get("author").get("id"), userId);
    }
}
