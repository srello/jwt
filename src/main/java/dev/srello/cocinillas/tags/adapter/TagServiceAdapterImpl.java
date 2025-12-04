package dev.srello.cocinillas.tags.adapter;

import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.tags.model.Tag;
import dev.srello.cocinillas.tags.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagServiceAdapterImpl implements TagServiceAdapter {
    private final TagService tagService;

    @Override
    public List<Tag> getTagsFromRecipeIDTO(RecipeIDTO recipeIDTO) {
        return tagService.getTagsById(recipeIDTO.getTagIds());
    }
}
