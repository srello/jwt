package dev.srello.cocinillas.tags.rdto;

import dev.srello.cocinillas.tags.enums.TagType;

import java.util.List;

public record GetTagsRQRDTO(
        String name,
        List<TagType> types
) {
}
