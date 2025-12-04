package dev.srello.cocinillas.menu.rdto;

import dev.srello.cocinillas.shared.enums.InteractionType;

public record MenuInteractionRSRDTO(
        Long id,
        Long userId,
        Long menuId,
        InteractionType type
) {
}
