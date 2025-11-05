package dev.srello.cocinillas.tags.service;

import dev.srello.cocinillas.tags.dto.GetTagsIDTO;
import dev.srello.cocinillas.tags.model.Tag;
import org.springframework.data.jpa.domain.Specification;

public interface TagSpecificationService {
    Specification<Tag> buildTagsPaginatedSpecification(GetTagsIDTO getTagsIDTO);
}
