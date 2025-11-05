package dev.srello.cocinillas.tags.service;

import dev.srello.cocinillas.tags.dto.GetTagsIDTO;
import dev.srello.cocinillas.tags.enums.TagType;
import dev.srello.cocinillas.tags.model.Tag;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@RequiredArgsConstructor
public class TagSpecificationServiceImpl implements TagSpecificationService {
    @Override
    public Specification<Tag> buildTagsPaginatedSpecification(GetTagsIDTO getTagsIDTO) {
        String name = ofNullable(getTagsIDTO.getName()).orElse(EMPTY);
        Specification<Tag> nameContains = nameContains(name);
        Specification<Tag> hasAtLeastOneType = ofNullable(getTagsIDTO.getTypes()).map(this::hasAtLeastOneType).orElse(null);

        return Stream.of(nameContains, hasAtLeastOneType)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(nameContains);
    }

    private Specification<Tag> nameContains(String filterString) {
        return (tagsTable, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(tagsTable.get("name")), "%" + filterString.toLowerCase() + "%");
    }

    private Specification<Tag> hasAtLeastOneType(List<TagType> types) {
        return (tagsTable, query, criteriaBuilder) ->
        {
            Join<Tag, TagType> tagTypeJoin = tagsTable.join("types");
            return tagTypeJoin.in(types);
        };
    }
}
