package dev.srello.cocinillas.recipe.enums;

import dev.srello.cocinillas.shared.enums.EnumMethods;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecipeVisibility implements EnumMethods {
    OFFICIAL(3),
    PUBLIC(2),
    PRIVATE(1);
    private final Integer visibilityValue;
}
