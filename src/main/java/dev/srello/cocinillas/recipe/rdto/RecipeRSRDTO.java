package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.shared.enums.Visibility;
import dev.srello.cocinillas.tags.rdto.TagRSRDTO;
import dev.srello.cocinillas.user.rdto.AuthorRSRDTO;

import java.net.URL;
import java.util.List;

public record RecipeRSRDTO(
        Long id,
        String name,
        String description,
        List<IngredientRSRDTO> ingredients,
        List<InstructionRSRDTO> instructions,
        Visibility visibility,
        List<TagRSRDTO> tags,
        List<URL> imageUrls,
        Integer totalDuration,
        MacrosRSRDTO macros,
        Long likes,
        Boolean isLiked,
        Boolean isSaved,
        AuthorRSRDTO author

) {
}
