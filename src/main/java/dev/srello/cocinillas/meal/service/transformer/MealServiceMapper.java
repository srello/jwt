package dev.srello.cocinillas.meal.service.transformer;

import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.user.service.transformer.UserServiceMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = UserServiceMapper.class)
public interface MealServiceMapper {
    @Mapping(target = "author.id", source = "userId")
    Meal toMeal(MealIDTO mealIDTO);

    @Mapping(target = "id", source = "meal.id")
    @Mapping(target = "name", source = "meal.name")
    MealODTO toMealODTO(Meal meal, List<RecipeODTO> recipes, @Context Long userId);

    MealODTO toMealODTO(Meal meal, @Context Long userId);
}
