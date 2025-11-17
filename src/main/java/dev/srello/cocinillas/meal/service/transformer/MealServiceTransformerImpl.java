package dev.srello.cocinillas.meal.service.transformer;

import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MealServiceTransformerImpl implements MealServiceTransformer {
    private final MealServiceMapper mapper;

    private static List<RecipeODTO> findRecipes(Meal meal, List<RecipeODTO> distinctRecipes) {
        return distinctRecipes.stream().filter(recipe -> meal.getRecipeIds().contains(recipe.getId()))
                .toList();
    }

    @Override
    public Meal toMeal(MealIDTO mealIDTO) {
        return mapper.toMeal(mealIDTO);
    }

    @Override
    public List<Meal> toMeals(List<MealIDTO> mealsIDTO) {
        return mealsIDTO.stream().map(this::toMeal).toList();
    }

    @Override
    public MealODTO toMealODTO(Meal meal, List<RecipeODTO> recipes) {
        return mapper.toMealODTO(meal, recipes);
    }

    @Override
    public MealODTO toMealODTO(Meal meal) {
        return mapper.toMealODTO(meal);
    }

    @Override
    public List<MealODTO> toMealsODTO(List<Meal> meals, List<RecipeODTO> recipeODTOS) {
        return meals.stream()
                .map(meal -> toMealODTO(meal, findRecipes(meal, recipeODTOS)))
                .toList();
    }
}
