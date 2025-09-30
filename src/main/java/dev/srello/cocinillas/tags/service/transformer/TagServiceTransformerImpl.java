package dev.srello.cocinillas.tags.service.transformer;

import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.tags.model.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagServiceTransformerImpl implements TagServiceTransformer {
    private final TagServiceMapper mapper;

    @Override
    public TagODTO toTagODTO(@NonNull Tag tag) {
        return mapper.toTagODTO(tag);
    }
}
