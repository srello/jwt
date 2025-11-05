package dev.srello.cocinillas.tags.service;

import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.tags.dto.GetTagsIDTO;
import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.tags.model.Tag;
import dev.srello.cocinillas.tags.repository.TagRepository;
import dev.srello.cocinillas.tags.service.transformer.TagServiceTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final TagServiceTransformer transformer;
    private final TagSpecificationService specificationService;

    @Override
    public Page<TagODTO> getTagsPaginated(GetTagsIDTO getTagsIDTO, PaginationIDTO paginationIDTO) {
        Specification<Tag> specification = specificationService.buildTagsPaginatedSpecification(getTagsIDTO);

        Page<Tag> tags = repository.findAll(specification, paginationIDTO.getPageRequest());
        return transformer.toTagsODTO(tags);
    }
}
