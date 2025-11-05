package dev.srello.cocinillas.tags.service;

import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.tags.dto.GetTagsIDTO;
import dev.srello.cocinillas.tags.dto.TagODTO;
import org.springframework.data.domain.Page;

public interface TagService {
    Page<TagODTO> getTagsPaginated(GetTagsIDTO getTagsIDTO, PaginationIDTO paginationIDTO);
}
