package dev.srello.cocinillas.core;

import org.springframework.data.jpa.domain.Specification;

import static org.springframework.util.StringUtils.hasLength;

public abstract class BaseSpecificationService<T> {
    protected Specification<T> emptySpecification() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    protected Specification<T> fieldContains(String filterString, String field) {
        if (!hasLength(filterString))
            return emptySpecification();
        return (recipeTable, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(recipeTable.get(field)), "%" + filterString.toLowerCase() + "%");
    }
}
