package dev.srello.cocinillas.tags.controller.transformer;

import dev.srello.cocinillas.tags.dto.GetTagsIDTO;
import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.tags.rdto.GetTagsRQRDTO;
import dev.srello.cocinillas.tags.rdto.TagRSRDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagControllerTransformerImpl implements TagControllerTransformer {
    private final TagControllerMapper mapper;

    @Override
    public Page<TagRSRDTO> toTagsRSRDTO(@NonNull Page<TagODTO> tags) {
        return tags.map(mapper::toTagRSRDTO);
    }

    @Override
    public GetTagsIDTO toGetTagsIDTO(@NonNull GetTagsRQRDTO getTagsRQRDTO) {
        return mapper.toGetTagsIDTO(getTagsRQRDTO);
    }
}
