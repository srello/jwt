package dev.srello.cocinillas.menu.rdto;

import dev.srello.cocinillas.shared.enums.InteractionType;
import org.jetbrains.annotations.NotNull;

public record MenuInteractionRQRDTO(
        @NotNull("Menu id is required.")
        Long menuId,
        @NotNull("Type id is required.")
        InteractionType type
) {
}
