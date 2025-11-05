package dev.srello.cocinillas.tags.service.transformer;

import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.tags.model.Tag;
import org.mapstruct.Mapper;

@Mapper
public interface TagServiceMapper {
    TagODTO toTagODTO(Tag tag);
}
