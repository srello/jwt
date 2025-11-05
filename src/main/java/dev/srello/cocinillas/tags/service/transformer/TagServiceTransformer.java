package dev.srello.cocinillas.tags.service.transformer;

import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.tags.model.Tag;
import lombok.NonNull;
import org.springframework.data.domain.Page;

public interface TagServiceTransformer {
    Page<TagODTO> toTagsODTO(@NonNull Page<Tag> tags);
}
