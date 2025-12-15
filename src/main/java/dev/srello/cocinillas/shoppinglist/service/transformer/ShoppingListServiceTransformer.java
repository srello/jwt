package dev.srello.cocinillas.shoppinglist.service.transformer;

import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.recipe.model.Ingredient;
import dev.srello.cocinillas.shoppinglist.dto.*;
import dev.srello.cocinillas.shoppinglist.model.ShoppingList;
import dev.srello.cocinillas.shoppinglist.model.ShoppingListItem;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShoppingListServiceTransformer {
    ShoppingList toShoppingList(@NonNull ShoppingListIDTO shoppingListIDTO, @NonNull List<Ingredient> ingredients);

    ShoppingListODTO toShoppingListODTO(@NonNull ShoppingList shoppingList);

    ShoppingListSummaryODTO toShoppingListSummaryODTO(@NonNull ShoppingList shoppingList);

    Page<ShoppingListSummaryODTO> toShoppingListSummariesODTO(@NonNull Page<ShoppingList> shoppingLists);

    ShoppingListItemODTO toShoppingListItemODTO(@NonNull ShoppingListItem savedShoppingListItem);

    List<ShoppingListItemODTO> toShoppingListItemsODTO(@NonNull List<ShoppingListItem> savedShoppingListItems);

    List<ShoppingListItem> toShoppingListItems(@NonNull List<ShoppingListItemIDTO> shoppingListItemIDTO, @NonNull List<Product> products);

    List<ShoppingListItem> toShoppingListItems(@NonNull List<Ingredient> ingredients);
}
