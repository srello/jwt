package dev.srello.cocinillas.tags.service;

import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.tags.dto.GetTagsIDTO;
import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.tags.model.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TagService {
    Page<TagODTO> getTagsPaginated(GetTagsIDTO getTagsIDTO, PaginationIDTO paginationIDTO);

    List<Tag> getTagsById(List<Long> tagIds);
}
