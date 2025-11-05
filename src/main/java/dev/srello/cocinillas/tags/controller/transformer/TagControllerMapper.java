package dev.srello.cocinillas.tags.controller.transformer;

import dev.srello.cocinillas.tags.dto.GetTagsIDTO;
import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.tags.rdto.GetTagsRQRDTO;
import dev.srello.cocinillas.tags.rdto.TagRSRDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TagControllerMapper {
    TagRSRDTO toTagRSRDTO(TagODTO tag);

    GetTagsIDTO toGetTagsIDTO(GetTagsRQRDTO getTagsRQRDTO);
}
