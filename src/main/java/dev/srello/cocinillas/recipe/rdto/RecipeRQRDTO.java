package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.core.validation.ValidContentTypes;
import dev.srello.cocinillas.recipe.enums.RecipeVisibility;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record RecipeRQRDTO(
        @NotEmpty(message = "Name can not be empty.")
        String name,
        @NotEmpty(message = "Description can not be empty.")
        String description,
        @NotNull("Ingredients can not be null or empty.")
        List<Long> ingredientIds,
        @NotNull("Instructions can not be null or empty.")
        List<@Valid InstructionRQRDTO> instructions,
        @NotEmpty(message = "Visibility can not be empty.")
        RecipeVisibility visibility,
        List<Long> tagIds,
        @ValidContentTypes
        List<String> imageContentTypes,
        @NotEmpty(message = "Total duration can not be empty.")
        Integer totalDuration

) {
}
