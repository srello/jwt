package dev.srello.cocinillas.tags.adapter;

import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.tags.model.Tag;

import java.util.List;

public interface TagServiceAdapter {
    List<Tag> getTagsFromRecipeIDTO(RecipeIDTO recipeIDTO);
}
