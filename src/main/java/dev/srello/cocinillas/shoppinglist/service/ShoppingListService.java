package dev.srello.cocinillas.shoppinglist.service;

import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.shoppinglist.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShoppingListService {
    ShoppingListSummaryODTO createShoppingList(ShoppingListIDTO shoppingListIDTO);

    Page<ShoppingListSummaryODTO> getShoppingListsPaginated(Long id, PaginationIDTO paginationIDTO);

    ShoppingListODTO getShoppingListById(GetShoppingListByIdIDTO getShoppingListByIdIDTO);

    ShoppingListODTO deleteShoppingList(DeleteShoppingListIDTO deleteShoppingListIDTO);

    ShoppingListItemODTO checkShoppingListItem(CheckItemIDTO checkItemIDTO);

    List<ShoppingListItemODTO> addItemsToShoppingList(AddShoppingListItemsIDTO addShoppingListItemsIDTO);

    ShoppingListItemODTO deleteShoppingListItem(DeleteShoppingListItemIDTO deleteShoppingListItemIDTO);

    ShoppingListODTO refreshShoppingList(RefreshShoppingListIDTO refreshShoppingListIDTO);
}
