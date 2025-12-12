package dev.srello.cocinillas.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckItemIDTO {
    private Long id;
    private Long itemId;
    private Long userId;
    private Boolean checked;
}
