package dev.srello.cocinillas.tags.service.transformer;

import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.tags.model.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagServiceTransformerImpl implements TagServiceTransformer {

    private final TagServiceMapper mapper;

    @Override
    public Page<TagODTO> toTagsODTO(@NonNull Page<Tag> tags) {
        return tags.map(mapper::toTagODTO);
    }
}
