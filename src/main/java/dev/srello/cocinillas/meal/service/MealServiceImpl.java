package dev.srello.cocinillas.meal.service;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.meal.dto.DeleteMealIDTO;
import dev.srello.cocinillas.meal.dto.GetMealsIDTO;
import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.meal.repository.MealRepository;
import dev.srello.cocinillas.meal.service.transformer.MealServiceTransformer;
import dev.srello.cocinillas.recipe.dto.GetRecipeIDTO;
import dev.srello.cocinillas.recipe.dto.GetRecipesByIdIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.MEAL_NOT_FOUND_CODE;
import static dev.srello.cocinillas.core.messages.Messages.Error.MEAL_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository repository;
    private final MealServiceTransformer transformer;
    private final RecipeService recipeService;

    @Override
    public MealODTO createMeal(MealIDTO mealIDTO) {
        Meal meal = transformer.toMeal(mealIDTO);
        Meal savedMeal = repository.saveAndFlush(meal);
        GetRecipeIDTO getRecipeIDTO = transformer.toGetRecipeIDTO(mealIDTO);
        RecipeODTO recipeODTO = recipeService.getRecipeById(getRecipeIDTO);
        return transformer.toMealODTO(savedMeal, recipeODTO);
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
        List<Meal> meals = repository.findByUserIdAndDateTimeBetweenOrderByDateTime(getMealsIDTO.getUserId(), getMealsIDTO.getStartDateTime(), getMealsIDTO.getEndDateTime());
        List<Long> recipeIds = meals.stream().map(Meal::getRecipeId).distinct().toList();
        GetRecipesByIdIDTO getRecipesByIdIDTO = transformer.toGetRecipesByIdIDTO(recipeIds, getMealsIDTO.getUserId());
        List<RecipeODTO> recipesODTO = recipeService.getRecipesById(getRecipesByIdIDTO);
        return transformer.toMealsODTO(meals, recipesODTO);
    }
}
