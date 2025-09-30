package dev.srello.cocinillas.tags.rdto;

import java.util.List;

public record TagRSRDTO(
        Long id,
        String name,
        List<TagTypeRSRDTO> tagTypes
) {
}
