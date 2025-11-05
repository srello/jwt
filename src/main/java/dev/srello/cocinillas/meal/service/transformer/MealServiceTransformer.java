package dev.srello.cocinillas.meal.service.transformer;

import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.recipe.dto.GetRecipeIDTO;
import dev.srello.cocinillas.recipe.dto.GetRecipesByIdIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;

import java.util.List;

public interface MealServiceTransformer {
    Meal toMeal(MealIDTO mealIDTO);

    MealODTO toMealODTO(Meal meal, RecipeODTO recipe);

    MealODTO toMealODTO(Meal meal);

    List<MealODTO> toMealsODTO(List<Meal> meals, List<RecipeODTO> recipeODTOS);

    GetRecipeIDTO toGetRecipeIDTO(MealIDTO mealIDTO);

    GetRecipesByIdIDTO toGetRecipesByIdIDTO(List<Long> recipeIds, Long userId);
}
