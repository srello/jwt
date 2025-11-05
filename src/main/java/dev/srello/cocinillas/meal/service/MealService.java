package dev.srello.cocinillas.meal.service;

import dev.srello.cocinillas.meal.dto.DeleteMealIDTO;
import dev.srello.cocinillas.meal.dto.GetMealsIDTO;
import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;

import java.util.List;

public interface MealService {
    MealODTO createMeal(MealIDTO mealIDTO);

    MealODTO deleteMeal(DeleteMealIDTO deleteMealIDTO);

    List<MealODTO> getMeals(GetMealsIDTO getMealsIDTO);

}
