package dev.srello.cocinillas.meal.adapter;

import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.shoppinglist.dto.ShoppingListIDTO;

import java.util.List;

public interface MealServiceAdapter {
    List<Meal> getMealsFromShoppingList(ShoppingListIDTO shoppingListIDTO);
}
