package dev.srello.cocinillas.shoppinglist.controller.transformer;

import dev.srello.cocinillas.shoppinglist.dto.*;
import dev.srello.cocinillas.shoppinglist.rdto.*;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShoppingListControllerTransformer {
    ShoppingListIDTO toShoppingListIDTO(@NonNull ShoppingListRQRDTO shoppingListRQRDTO, @NonNull Long authorId);

    ShoppingListRSRDTO toShoppingListRSRDTO(@NonNull ShoppingListODTO shoppingListODTO);

    ShoppingListSummaryRSRDTO toShoppingListSummaryRSRDTO(@NonNull ShoppingListSummaryODTO shoppingListSummaryODTO);

    Page<ShoppingListSummaryRSRDTO> toShoppingListSummariesRSRDTO(@NonNull Page<ShoppingListSummaryODTO> shoppingListSummariesODTOS);

    GetShoppingListByIdIDTO toGetShoppingListByIdIDTO(@NonNull Long shoppingListId, @NonNull Long userId);

    DeleteShoppingListIDTO toDeleteShoppingListIDTO(@NonNull Long shoppingListId, @NonNull Long userId);

    CheckItemIDTO toCheckItemIDTO(@NonNull Long id, @NonNull CheckItemRQRDTO checkItemRQRDTO, @NonNull Long userId);

    ShoppingListItemRSRDTO toShoppingListItemRSRDTO(@NonNull ShoppingListItemODTO shoppingListItemODTO);

    List<ShoppingListItemRSRDTO> toShoppingListItemsRSRDTO(@NonNull List<ShoppingListItemODTO> shoppingListItemODTO);

    AddShoppingListItemsIDTO toAddShoppingListItemIDTO(@NonNull AddShoppingListItemsRQRDTO addShoppingListItemsRQRDTO, @NonNull Long userId);

    DeleteShoppingListItemIDTO toDeleteShoppingListItemIDTO(@NonNull Long shoppingListId, @NonNull Long itemId, @NonNull Long userId);

    RefreshShoppingListIDTO toRefreshShoppingListIDTO(@NonNull Long shoppingListId, @NonNull ShoppingListRQRDTO shoppingListRQRDTO, @NonNull Long userId);
}
