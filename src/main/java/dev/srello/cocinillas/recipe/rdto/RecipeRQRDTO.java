package dev.srello.cocinillas.recipe.rdto;

import dev.srello.cocinillas.core.validation.ValidContentTypes;
import dev.srello.cocinillas.shared.enums.Visibility;
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
        List<IngredientRQRDTO> ingredients,
        @NotNull("Instructions can not be null or empty.")
        List<@Valid InstructionRQRDTO> instructions,
        @NotNull("Visibility can not be empty.")
        Visibility visibility,
        List<Long> tagIds,
        @ValidContentTypes
        List<String> imageContentTypes,
        @NotNull("Total duration can not be empty.")
        Long totalDuration

) {
}
