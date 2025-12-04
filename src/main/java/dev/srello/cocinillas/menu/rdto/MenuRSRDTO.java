package dev.srello.cocinillas.menu.rdto;

import dev.srello.cocinillas.shared.enums.Visibility;
import dev.srello.cocinillas.tags.rdto.TagRSRDTO;
import dev.srello.cocinillas.user.rdto.AuthorRSRDTO;

import java.util.List;

public record MenuRSRDTO(
        Long id,
        String name,
        String description,
        List<MenuMealRSRDTO> menuMeals,
        AuthorRSRDTO author,
        List<TagRSRDTO> tags,
        Long likes,
        Visibility visibility,
        Boolean isLiked,
        Boolean isSaved
) {
}
