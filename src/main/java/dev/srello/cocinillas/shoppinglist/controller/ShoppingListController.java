package dev.srello.cocinillas.shoppinglist.controller;


import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.shared.pagination.rdto.PaginationRQRDTO;
import dev.srello.cocinillas.shared.pagination.transformer.PaginationTransformer;
import dev.srello.cocinillas.shoppinglist.controller.transformer.ShoppingListControllerTransformer;
import dev.srello.cocinillas.shoppinglist.dto.*;
import dev.srello.cocinillas.shoppinglist.rdto.*;
import dev.srello.cocinillas.shoppinglist.service.ShoppingListService;
import dev.srello.cocinillas.user.CurrentUser;
import dev.srello.cocinillas.user.dto.UserODTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.srello.cocinillas.core.request.RequestConstants.ID_PATH_VARIABLE;
import static dev.srello.cocinillas.core.request.RequestConstants.REFRESH_PATH_VARIABLE;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shopping-lists")
public class ShoppingListController {
    public static final String ITEMS_PATH = "/items";
    private final ShoppingListService service;
    private final ShoppingListControllerTransformer transformer;
    private final PaginationTransformer paginationTransformer;

    @PostMapping
    public ResponseEntity<ShoppingListSummaryRSRDTO> createShoppingList(@RequestBody ShoppingListRQRDTO shoppingListRQRDTO, @CurrentUser UserODTO currentUser) {
        ShoppingListIDTO shoppingListIDTO = transformer.toShoppingListIDTO(shoppingListRQRDTO, currentUser.getId());
        ShoppingListSummaryODTO shoppingListSummaryODTO = service.createShoppingList(shoppingListIDTO);
        ShoppingListSummaryRSRDTO shoppingListSummaryRSRDTO = transformer.toShoppingListSummaryRSRDTO(shoppingListSummaryODTO);
        return ok(shoppingListSummaryRSRDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ShoppingListSummaryRSRDTO>> getShoppingListsPaginated(@Valid PaginationRQRDTO paginationRQRDTO, @CurrentUser UserODTO currentUser) {
        PaginationIDTO paginationIDTO = paginationTransformer.toPaginationIDTO(paginationRQRDTO);
        Page<ShoppingListSummaryODTO> shoppingListSummaryODTOS = service.getShoppingListsPaginated(currentUser.getId(), paginationIDTO);
        Page<ShoppingListSummaryRSRDTO> shoppingListSummariesRSRDTO = transformer.toShoppingListSummariesRSRDTO(shoppingListSummaryODTOS);
        return ok(shoppingListSummariesRSRDTO);
    }

    @GetMapping(ID_PATH_VARIABLE)
    public ResponseEntity<ShoppingListRSRDTO> getShoppingListById(@PathVariable Long id, @CurrentUser UserODTO currentUser) {
        GetShoppingListByIdIDTO getShoppingListByIdIDTO = transformer.toGetShoppingListByIdIDTO(id, currentUser.getId());
        ShoppingListODTO shoppingListODTO = service.getShoppingListById(getShoppingListByIdIDTO);
        ShoppingListRSRDTO shoppingListRSRDTO = transformer.toShoppingListRSRDTO(shoppingListODTO);
        return ok(shoppingListRSRDTO);
    }

    @DeleteMapping(ID_PATH_VARIABLE)
    public ResponseEntity<ShoppingListRSRDTO> deleteShoppingList(@PathVariable Long id, @CurrentUser UserODTO currentUser) {
        DeleteShoppingListIDTO deleteShoppingListIDTO = transformer.toDeleteShoppingListIDTO(id, currentUser.getId());
        ShoppingListODTO shoppingListODTO = service.deleteShoppingList(deleteShoppingListIDTO);
        ShoppingListRSRDTO shoppingListRSRDTO = transformer.toShoppingListRSRDTO(shoppingListODTO);
        return ok(shoppingListRSRDTO);
    }

    @PatchMapping(ID_PATH_VARIABLE)
    public ResponseEntity<ShoppingListItemRSRDTO> checkShoppingListItem(@PathVariable Long id, @RequestBody CheckItemRQRDTO checkItemRQRDTO, @CurrentUser UserODTO currentUser) {
        CheckItemIDTO checkItemIDTO = transformer.toCheckItemIDTO(id, checkItemRQRDTO, currentUser.getId());
        ShoppingListItemODTO shoppingListItemODTO = service.checkShoppingListItem(checkItemIDTO);
        ShoppingListItemRSRDTO shoppingListItemRSRDTO = transformer.toShoppingListItemRSRDTO(shoppingListItemODTO);
        return ok(shoppingListItemRSRDTO);
    }

    @PostMapping(ITEMS_PATH)
    public ResponseEntity<List<ShoppingListItemRSRDTO>> addItemsToShoppingList(@RequestBody AddShoppingListItemsRQRDTO addShoppingListItemsRQRDTO, @CurrentUser UserODTO currentUser) {
        AddShoppingListItemsIDTO addShoppingListItemsIDTO = transformer.toAddShoppingListItemIDTO(addShoppingListItemsRQRDTO, currentUser.getId());
        List<ShoppingListItemODTO> shoppingListItemsODTO = service.addItemsToShoppingList(addShoppingListItemsIDTO);
        List<ShoppingListItemRSRDTO> shoppingListItemsRSRDTO = transformer.toShoppingListItemsRSRDTO(shoppingListItemsODTO);
        return ok(shoppingListItemsRSRDTO);
    }

    @DeleteMapping(ID_PATH_VARIABLE + ITEMS_PATH + "/{itemId}")
    public ResponseEntity<ShoppingListItemRSRDTO> removeShoppingListItem(@PathVariable Long id, @PathVariable Long itemId, @CurrentUser UserODTO currentUser) {
        DeleteShoppingListItemIDTO deleteShoppingListItemIDTO = transformer.toDeleteShoppingListItemIDTO(id, itemId, currentUser.getId());
        ShoppingListItemODTO shoppingListItemODTO = service.deleteShoppingListItem(deleteShoppingListItemIDTO);
        ShoppingListItemRSRDTO shoppingListItemRSRDTO = transformer.toShoppingListItemRSRDTO(shoppingListItemODTO);
        return ok(shoppingListItemRSRDTO);
    }

    @PatchMapping(ID_PATH_VARIABLE + REFRESH_PATH_VARIABLE)
    public ResponseEntity<ShoppingListRSRDTO> refreshShoppingList(@PathVariable Long id, @RequestBody ShoppingListRQRDTO shoppingListRQRDTO, @CurrentUser UserODTO currentUser) {
        RefreshShoppingListIDTO refreshShoppingListIDTO = transformer.toRefreshShoppingListIDTO(id, shoppingListRQRDTO, currentUser.getId());
        ShoppingListODTO shoppingListODTO = service.refreshShoppingList(refreshShoppingListIDTO);
        ShoppingListRSRDTO shoppingListRSRDTO = transformer.toShoppingListRSRDTO(shoppingListODTO);
        return ok(shoppingListRSRDTO);
    }

}
