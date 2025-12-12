package dev.srello.cocinillas.product.adapter;

import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.shoppinglist.dto.ShoppingListItemIDTO;

import java.util.List;

public interface ProductServiceAdapter {
    List<Product> getProductsFromRecipeIDTO(RecipeIDTO recipeIDTO);

    List<Product> getProductsFromShoppingListItems(List<ShoppingListItemIDTO> shoppingListItems);

    List<Product> getProductsFromMeals(List<MealODTO> meals);

}
