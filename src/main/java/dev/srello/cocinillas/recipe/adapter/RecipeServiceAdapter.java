package dev.srello.cocinillas.recipe.adapter;

import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Ingredient;

import java.util.List;

public interface RecipeServiceAdapter {
    List<RecipeODTO> getRecipesFromMenus(List<Menu> menus, Long userId);

    List<RecipeODTO> getRecipesFromMeals(List<Meal> meals, Long userId);

    List<Ingredient> getIngredientsFromMeals(List<Meal> meals);

}
