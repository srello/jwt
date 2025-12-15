package dev.srello.cocinillas.meal.adapter.transformer;

import dev.srello.cocinillas.meal.dto.GetMealsIDTO;
import dev.srello.cocinillas.shoppinglist.dto.RefreshShoppingListIDTO;
import dev.srello.cocinillas.shoppinglist.dto.ShoppingListIDTO;
import lombok.NonNull;

public interface MealServiceAdapterTransformer {
    GetMealsIDTO toGetMealsIDTO(@NonNull ShoppingListIDTO shoppingListIDTO);

    GetMealsIDTO toGetMealsIDTO(@NonNull RefreshShoppingListIDTO refreshShoppingListIDTO);
}
