package dev.srello.cocinillas.menu.dto;

import dev.srello.cocinillas.shared.enums.InteractionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuInteractionIDTO {
    private Long userId;
    private Long menuId;
    private InteractionType type;
}
