package dev.srello.cocinillas.shoppinglist.service.transformer;

import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.recipe.model.Ingredient;
import dev.srello.cocinillas.shoppinglist.dto.*;
import dev.srello.cocinillas.shoppinglist.model.ShoppingList;
import dev.srello.cocinillas.shoppinglist.model.ShoppingListItem;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShoppingListServiceTransformerImpl implements ShoppingListServiceTransformer {
    private final ShoppingListServiceMapper mapper;

    @Override
    public ShoppingList toShoppingList(@NotNull ShoppingListIDTO shoppingListIDTO, @NonNull List<Ingredient> ingredients) {
        List<ShoppingListItem> items = ingredients.stream().map(mapper::toShoppingListItem).toList();
        return mapper.toShoppingList(shoppingListIDTO, items);
    }

    @Override
    public ShoppingListODTO toShoppingListODTO(@NonNull ShoppingList shoppingList) {
        return mapper.toShoppingListODTO(shoppingList);
    }

    @Override
    public ShoppingListSummaryODTO toShoppingListSummaryODTO(@NonNull ShoppingList shoppingList) {
        return mapper.toShoppingListSummaryODTO(shoppingList);
    }

    @Override
    public Page<ShoppingListSummaryODTO> toShoppingListSummariesODTO(@NonNull Page<ShoppingList> shoppingLists) {
        return shoppingLists.map(mapper::toShoppingListSummaryODTO);
    }

    @Override
    public ShoppingListItemODTO toShoppingListItemODTO(@NonNull ShoppingListItem savedShoppingListItem) {
        return mapper.toShoppingListItemODTO(savedShoppingListItem);
    }

    @Override
    public List<ShoppingListItemODTO> toShoppingListItemsODTO(@NonNull List<ShoppingListItem> savedShoppingListItems) {
        return savedShoppingListItems.stream().map(mapper::toShoppingListItemODTO).toList();
    }

    @Override
    public List<ShoppingListItem> toShoppingListItems(@NonNull List<ShoppingListItemIDTO> shoppingListItemIDTO, @NonNull List<Product> products) {
        return shoppingListItemIDTO.stream()
                .map(item -> mapper.toShoppingListItem(item, products))
                .toList();
    }

    @Override
    public List<ShoppingListItem> toShoppingListItems(@NonNull List<Ingredient> ingredients) {
        return ingredients.stream().map(mapper::toShoppingListItem).toList();
    }
}
