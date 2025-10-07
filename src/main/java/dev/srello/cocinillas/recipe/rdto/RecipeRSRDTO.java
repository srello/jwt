package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.recipe.enums.RecipeVisibility;
import dev.srello.cocinillas.tags.rdto.TagRSRDTO;

import java.net.URL;
import java.util.List;

public record RecipeRSRDTO(
        Long id,
        String name,
        List<IngredientRSRDTO> ingredients,
        List<InstructionRSRDTO> instructions,
        RecipeVisibility visibility,
        List<TagRSRDTO> tags,
        List<URL> imageUrls,
        Integer totalDuration,
        MacrosRSRDTO macros

) {
}
