package dev.srello.cocinillas.menu.enums;

import dev.srello.cocinillas.shared.enums.EnumMethods;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MenuVisibility implements EnumMethods {
    OFFICIAL(3),
    PUBLIC(2),
    PRIVATE(1);
    private final Integer visibilityValue;
}
