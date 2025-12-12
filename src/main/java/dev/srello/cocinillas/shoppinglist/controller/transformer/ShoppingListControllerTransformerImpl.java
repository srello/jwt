package dev.srello.cocinillas.shoppinglist.controller.transformer;

import dev.srello.cocinillas.shoppinglist.dto.*;
import dev.srello.cocinillas.shoppinglist.rdto.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShoppingListControllerTransformerImpl implements ShoppingListControllerTransformer {
    private final ShoppingListControllerMapper mapper;

    @Override
    public ShoppingListIDTO toShoppingListIDTO(@NotNull ShoppingListRQRDTO shoppingListRQRDTO, @NotNull Long authorId) {
        return mapper.toShoppingListIDTO(shoppingListRQRDTO, authorId);
    }

    @Override
    public ShoppingListRSRDTO toShoppingListRSRDTO(@NotNull ShoppingListODTO shoppingListODTO) {
        return mapper.toShoppingListRSRDTO(shoppingListODTO);
    }

    @Override
    public ShoppingListSummaryRSRDTO toShoppingListSummaryRSRDTO(@NotNull ShoppingListSummaryODTO shoppingListSummaryODTO) {
        return mapper.toShoppingListSummaryRSRDTO(shoppingListSummaryODTO);
    }

    @Override
    public Page<ShoppingListSummaryRSRDTO> toShoppingListSummariesRSRDTO(@NotNull Page<ShoppingListSummaryODTO> shoppingListSummariesODTOS) {
        return shoppingListSummariesODTOS.map(mapper::toShoppingListSummaryRSRDTO);
    }

    @Override
    public GetShoppingListByIdIDTO toGetShoppingListByIdIDTO(@NotNull Long shoppingListId, @NotNull Long userId) {
        return mapper.toGetShoppingListByIdIDTO(shoppingListId, userId);
    }

    @Override
    public DeleteShoppingListIDTO toDeleteShoppingListIDTO(@NotNull Long shoppingListId, @NotNull Long userId) {
        return mapper.toDeleteShoppingListIDTO(shoppingListId, userId);
    }

    @Override
    public CheckItemIDTO toCheckItemIDTO(@NotNull Long id, @NotNull CheckItemRQRDTO checkItemRQRDTO, @NotNull Long userId) {
        return mapper.toCheckItemIDTO(id, checkItemRQRDTO, userId);
    }

    @Override
    public ShoppingListItemRSRDTO toShoppingListItemRSRDTO(@NotNull ShoppingListItemODTO shoppingListItemODTO) {
        return mapper.toShoppingListItemRSRDTO(shoppingListItemODTO);
    }

    @Override
    public List<ShoppingListItemRSRDTO> toShoppingListItemsRSRDTO(@NonNull List<ShoppingListItemODTO> shoppingListItemsODTO) {
        return shoppingListItemsODTO.stream().map(mapper::toShoppingListItemRSRDTO).toList();
    }

    @Override
    public AddShoppingListItemsIDTO toAddShoppingListItemIDTO(@NonNull AddShoppingListItemsRQRDTO addShoppingListItemsRQRDTO, @NonNull Long userId) {
        return mapper.toAddShoppingListItemIDTO(addShoppingListItemsRQRDTO, userId);
    }

    @Override
    public DeleteShoppingListItemIDTO toDeleteShoppingListItemIDTO(@NonNull Long shoppingListId, @NonNull Long itemId, @NonNull Long userId) {
        return mapper.toDeleteShoppingListItemIDTO(shoppingListId, itemId, userId);
    }
}
