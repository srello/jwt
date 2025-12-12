package dev.srello.cocinillas.shoppinglist.controller.transformer;

import dev.srello.cocinillas.product.controller.transformer.ProductControllerMapper;
import dev.srello.cocinillas.shoppinglist.dto.*;
import dev.srello.cocinillas.shoppinglist.rdto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ProductControllerMapper.class)
public interface ShoppingListControllerMapper {

    @Mapping(target = "completed", constant = "false")
    ShoppingListIDTO toShoppingListIDTO(ShoppingListRQRDTO shoppingListRQRDTO, Long authorId);

    ShoppingListRSRDTO toShoppingListRSRDTO(ShoppingListODTO shoppingListODTO);

    ShoppingListSummaryRSRDTO toShoppingListSummaryRSRDTO(ShoppingListSummaryODTO shoppingListSummaryODTO);

    GetShoppingListByIdIDTO toGetShoppingListByIdIDTO(Long shoppingListId, Long userId);

    DeleteShoppingListIDTO toDeleteShoppingListIDTO(Long shoppingListId, Long userId);

    CheckItemIDTO toCheckItemIDTO(Long id, CheckItemRQRDTO checkItemRQRDTO, Long userId);

    ShoppingListItemRSRDTO toShoppingListItemRSRDTO(ShoppingListItemODTO shoppingListItemODTO);

    AddShoppingListItemsIDTO toAddShoppingListItemIDTO(AddShoppingListItemsRQRDTO addShoppingListItemsRQRDTO, Long userId);

    DeleteShoppingListItemIDTO toDeleteShoppingListItemIDTO(Long shoppingListId, Long itemId, Long userId);
}
