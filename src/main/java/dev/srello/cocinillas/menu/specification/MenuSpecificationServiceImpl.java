package dev.srello.cocinillas.menu.specification;

import dev.srello.cocinillas.menu.dto.GetMenusIDTO;
import dev.srello.cocinillas.menu.enums.MenuVisibility;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.menu.model.MenuInteraction;
import dev.srello.cocinillas.tags.model.Tag;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static dev.srello.cocinillas.menu.enums.MenuInteractionType.SAVE;
import static dev.srello.cocinillas.menu.enums.MenuVisibility.*;
import static jakarta.persistence.criteria.JoinType.LEFT;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;


@Service
@RequiredArgsConstructor
public class MenuSpecificationServiceImpl implements MenuSpecificationService {
    @Override
    public Specification<Menu> buildMenusPaginatedSpecification(GetMenusIDTO getMenusIDTO) {
        Specification<Menu> nameSpecification = ofNullable(getMenusIDTO.getName()).map(this::nameContains).orElse(null);
        Specification<Menu> tagsSpecification = ofNullable(getMenusIDTO.getTags()).map(this::tagsContainsAll).orElse(null);
        Specification<Menu> visibilitySpecification = isInVisibilityNotPrivate(getMenusIDTO.getVisibility());

        return Stream.of(nameSpecification, tagsSpecification, visibilitySpecification)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(visibilitySpecification);
    }

    @Override
    public Specification<Menu> buildUserMenusPaginatedSpecification(GetMenusIDTO getMenusIDTO) {
        Specification<Menu> nameSpecification = ofNullable(getMenusIDTO.getName()).map(this::nameContains).orElse(null);
        Specification<Menu> userSpecification = userMenus(getMenusIDTO.getUserId());
        return Stream.of(nameSpecification, userSpecification)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(userSpecification);
    }

    private Specification<Menu> userMenus(Long userId) {
        return (menuTable, query, cb) -> {
            Predicate isAuthor = cb.equal(menuTable.get("author").get("id"), userId);
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<MenuInteraction> interaction = subquery.from(MenuInteraction.class);
            subquery.select(interaction.get("menuId"))
                    .where(
                            cb.equal(interaction.get("userId"), userId),
                            cb.equal(interaction.get("type"), SAVE)
                    );
            Predicate isSaved = menuTable.get("id").in(subquery);
            return cb.or(isAuthor, isSaved);
        };
    }

    private Specification<Menu> nameContains(String filterString) {
        return (menuTable, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(menuTable.get("name")), "%" + filterString.toLowerCase() + "%");
    }

    private Specification<Menu> tagsContainsAll(List<String> tags) {
        return (menuTable, query, cb) -> {
            query.distinct(true);

            Join<Menu, Tag> tagsJoin = menuTable.join("tags", LEFT);

            List<String> lowerCaseTags = tags.stream()
                    .map(String::toLowerCase)
                    .toList();

            Predicate inTags = cb.lower(tagsJoin.get("name")).in(lowerCaseTags);
            query.where(inTags);
            query.groupBy(menuTable.get("id"));
            Predicate havingCount = cb.equal(cb.countDistinct(tagsJoin.get("name")), lowerCaseTags.size());
            query.having(havingCount);
            return query.getRestriction();
        };
    }

    private Specification<Menu> isInVisibilityNotPrivate(MenuVisibility visibility) {

        return (menuTable, query, criteriaBuilder) ->
        {
            In<Integer> inClause = criteriaBuilder.in(menuTable.get("visibility"));
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
