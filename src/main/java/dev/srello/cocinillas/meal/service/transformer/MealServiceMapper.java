package dev.srello.cocinillas.meal.service.transformer;

import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.recipe.dto.GetRecipeIDTO;
import dev.srello.cocinillas.recipe.dto.GetRecipesByIdIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MealServiceMapper {
    Meal toMeal(MealIDTO mealIDTO);

    @Mapping(target = "id", source = "meal.id")
    @Mapping(target = "name", source = "meal.name")
    MealODTO toMealODTO(Meal meal, RecipeODTO recipe);

    MealODTO toMealODTO(Meal meal);

    @Mapping(target = "id", source = "recipeId")
    GetRecipeIDTO toGetRecipeIDTO(MealIDTO mealIDTO);

    GetRecipesByIdIDTO toGetRecipesByIdIDTO(List<Long> recipeIds, Long userId);
}
