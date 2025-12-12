package dev.srello.cocinillas.shoppinglist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetShoppingListByIdIDTO {
    private Long shoppingListId;
    private Long userId;
}
