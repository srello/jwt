package dev.srello.cocinillas.tags.controller.transformer;

import dev.srello.cocinillas.tags.dto.GetTagsIDTO;
import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.tags.rdto.GetTagsRQRDTO;
import dev.srello.cocinillas.tags.rdto.TagRSRDTO;
import lombok.NonNull;
import org.springframework.data.domain.Page;

public interface TagControllerTransformer {
    Page<TagRSRDTO> toTagsRSRDTO(@NonNull Page<TagODTO> tags);

    GetTagsIDTO toGetTagsIDTO(@NonNull GetTagsRQRDTO getTagsRQRDTO);
}
