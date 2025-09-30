package dev.srello.cocinillas.tags.service.transformer;

import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.tags.model.Tag;
import lombok.NonNull;

public interface TagServiceTransformer {
    TagODTO toTagODTO(@NonNull Tag tag);
}
