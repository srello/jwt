package dev.srello.cocinillas.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Visibility implements EnumMethods {
    OFFICIAL(3),
    PUBLIC(2),
    PRIVATE(1);
    private final Integer visibilityValue;
}
