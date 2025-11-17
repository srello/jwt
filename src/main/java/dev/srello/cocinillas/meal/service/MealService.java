package dev.srello.cocinillas.meal.service;

import dev.srello.cocinillas.meal.dto.*;

import java.util.List;

public interface MealService {
    MealODTO createMeal(MealIDTO mealIDTO);

    List<MealODTO> createMeals(List<MealIDTO> mealIDTO);

    MealODTO deleteMeal(DeleteMealIDTO deleteMealIDTO);

    List<MealODTO> getMeals(GetMealsIDTO getMealsIDTO);

    List<MealODTO> deleteMeals(DeleteMealsIDTO deleteMealsIDTO);
}
