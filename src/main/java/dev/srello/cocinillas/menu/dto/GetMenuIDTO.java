package dev.srello.cocinillas.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMenuIDTO {
    private Long menuId;
    private Long userId;
}
