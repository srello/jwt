package dev.srello.cocinillas.tags.rdto;

import dev.srello.cocinillas.tags.enums.TagType;

import java.util.List;

public record TagRSRDTO(
        Long id,
        String name,
        List<TagType> types
) {
}
