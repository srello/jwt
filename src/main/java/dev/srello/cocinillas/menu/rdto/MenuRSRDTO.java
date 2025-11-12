package dev.srello.cocinillas.menu.rdto;

import dev.srello.cocinillas.menu.enums.MenuVisibility;
import dev.srello.cocinillas.tags.rdto.TagRSRDTO;
import dev.srello.cocinillas.user.model.User;

import java.util.List;

public record MenuRSRDTO(
        Long id,
        String name,
        List<MenuMealRSRDTO> menuMeals,
        User author,
        List<TagRSRDTO> tags,
        Long likes,
        MenuVisibility visibility
) {
}
