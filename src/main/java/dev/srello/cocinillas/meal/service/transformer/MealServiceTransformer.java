package dev.srello.cocinillas.meal.service.transformer;

import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import lombok.NonNull;

import java.util.List;

public interface MealServiceTransformer {
    Meal toMeal(@NonNull MealIDTO mealIDTO);

    List<Meal> toMeals(@NonNull List<MealIDTO> mealsIDTO);

    MealODTO toMealODTO(@NonNull Meal meal, @NonNull List<RecipeODTO> recipe, @NonNull Long userId);

    MealODTO toMealODTO(@NonNull Meal meal, @NonNull Long userId);

    List<MealODTO> toMealsODTO(@NonNull List<Meal> meals, @NonNull List<RecipeODTO> recipeODTOS, @NonNull Long userId);
}
