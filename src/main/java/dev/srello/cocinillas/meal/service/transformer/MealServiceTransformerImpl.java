package dev.srello.cocinillas.meal.service.transformer;

import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
    public Meal toMeal(@NotNull MealIDTO mealIDTO) {
        return mapper.toMeal(mealIDTO);
    }

    @Override
    public List<Meal> toMeals(@NotNull List<MealIDTO> mealsIDTO) {
        return mealsIDTO.stream().map(this::toMeal).toList();
    }

    @Override
    public MealODTO toMealODTO(@NotNull Meal meal, @NotNull List<RecipeODTO> recipes, @NotNull Long userId) {
        return mapper.toMealODTO(meal, recipes, userId);
    }

    @Override
    public MealODTO toMealODTO(@NotNull Meal meal, @NotNull Long userId) {
        return mapper.toMealODTO(meal, userId);
    }

    @Override
    public List<MealODTO> toMealsODTO(@NotNull List<Meal> meals, @NotNull List<RecipeODTO> recipeODTOS, @NotNull Long userId) {
        return meals.stream()
                .map(meal -> toMealODTO(meal, findRecipes(meal, recipeODTOS), userId))
                .toList();
    }
}
