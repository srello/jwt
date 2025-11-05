package dev.srello.cocinillas.meal.service.transformer;

import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.recipe.dto.GetRecipeIDTO;
import dev.srello.cocinillas.recipe.dto.GetRecipesByIdIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MealServiceTransformerImpl implements MealServiceTransformer {
    private final MealServiceMapper mapper;

    @Nullable
    private static RecipeODTO findRecipe(Meal meal, List<RecipeODTO> distinctRecipes) {
        return distinctRecipes.stream().filter(recipe -> meal.getRecipeId().equals(recipe.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Meal toMeal(MealIDTO mealIDTO) {
        return mapper.toMeal(mealIDTO);
    }

    @Override
    public MealODTO toMealODTO(Meal meal, RecipeODTO recipe) {
        return mapper.toMealODTO(meal, recipe);
    }

    @Override
    public MealODTO toMealODTO(Meal meal) {
        return mapper.toMealODTO(meal);
    }

    @Override
    public List<MealODTO> toMealsODTO(List<Meal> meals, List<RecipeODTO> recipeODTOS) {
        return meals.stream()
                .map(meal -> toMealODTO(meal, findRecipe(meal, recipeODTOS)))
                .toList();
    }

    @Override
    public GetRecipeIDTO toGetRecipeIDTO(MealIDTO mealIDTO) {
        return mapper.toGetRecipeIDTO(mealIDTO);
    }

    @Override
    public GetRecipesByIdIDTO toGetRecipesByIdIDTO(List<Long> recipeIds, Long userId) {
        return mapper.toGetRecipesByIdIDTO(recipeIds, userId);
    }
}
