package dev.srello.cocinillas.meal.service;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.meal.dto.*;
import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.meal.repository.MealRepository;
import dev.srello.cocinillas.meal.service.transformer.MealServiceTransformer;
import dev.srello.cocinillas.recipe.adapter.RecipeServiceAdapter;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.MEALS_CAN_NOT_BE_EMPTY_CODE;
import static dev.srello.cocinillas.core.codes.messages.Codes.Error.MEAL_NOT_FOUND_CODE;
import static dev.srello.cocinillas.core.messages.Messages.Error.MEALS_CAN_NOT_BE_EMPTY;
import static dev.srello.cocinillas.core.messages.Messages.Error.MEAL_NOT_FOUND;
import static java.util.Collections.emptyList;
import static java.util.List.of;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository repository;
    private final MealServiceTransformer transformer;
    private final RecipeServiceAdapter recipeServiceAdapter;

    @Override
    public MealODTO createMeal(MealIDTO mealIDTO) {
        Meal meal = transformer.toMeal(mealIDTO);
        Meal savedMeal = repository.saveAndFlush(meal);
        List<RecipeODTO> recipes = recipeServiceAdapter.getRecipesFromMeals(of(meal), mealIDTO.getUserId());
        return transformer.toMealODTO(savedMeal, recipes);
    }

    @Override
    public List<MealODTO> createMeals(List<MealIDTO> mealsIDTO) {
        if (mealsIDTO.isEmpty())
            throw new RequestException(BAD_REQUEST, MEALS_CAN_NOT_BE_EMPTY, MEALS_CAN_NOT_BE_EMPTY_CODE);

        List<Meal> meals = transformer.toMeals(mealsIDTO);
        List<Meal> savedMeals = repository.saveAllAndFlush(meals);
        List<RecipeODTO> recipes = recipeServiceAdapter.getRecipesFromMeals(savedMeals, mealsIDTO.getFirst().getUserId());
        return transformer.toMealsODTO(savedMeals, recipes);
    }

    @Override
    public MealODTO deleteMeal(DeleteMealIDTO deleteMealIDTO) {
        Meal meal = repository.findByIdAndUserId(deleteMealIDTO.getMealId(), deleteMealIDTO.getUserId())
                .orElseThrow(() -> new RequestException(NOT_FOUND, MEAL_NOT_FOUND, MEAL_NOT_FOUND_CODE));
        repository.delete(meal);
        return transformer.toMealODTO(meal);
    }

    @Override
    public List<MealODTO> getMeals(GetMealsIDTO getMealsIDTO) {
        List<Meal> meals = getModelMeals(getMealsIDTO);
        List<RecipeODTO> recipes = recipeServiceAdapter.getRecipesFromMeals(meals, getMealsIDTO.getUserId());
        return transformer.toMealsODTO(meals, recipes);
    }

    @Override
    public List<Meal> getModelMeals(GetMealsIDTO getMealsIDTO) {
        return repository.findByUserIdAndDateTimeBetweenOrderByDateTime(getMealsIDTO.getUserId(), getMealsIDTO.getStartDateTime(), getMealsIDTO.getEndDateTime());
    }

    @Override
    public List<MealODTO> deleteMeals(DeleteMealsIDTO deleteMealsIDTO) {
        Long userId = deleteMealsIDTO.getUserId();
        List<Meal> meals = repository.findByUserIdAndDateTimeBetween(userId, deleteMealsIDTO.getStartDateTime(), deleteMealsIDTO.getEndDateTime());
        repository.deleteAll(meals);
        return transformer.toMealsODTO(meals, emptyList());
    }
}
