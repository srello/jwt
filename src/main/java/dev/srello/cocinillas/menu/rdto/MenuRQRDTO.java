package dev.srello.cocinillas.menu.rdto;

import dev.srello.cocinillas.tags.rdto.TagRSRDTO;
import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record MenuRQRDTO(
        @NotEmpty(message = "Name is required.")
        String name,
        @NotNull("Menu days are required.")
        List<MenuMealRQRDTO> menuMeals,
        List<TagRSRDTO> tags
) {
}
