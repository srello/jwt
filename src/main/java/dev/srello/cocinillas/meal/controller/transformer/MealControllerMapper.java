package dev.srello.cocinillas.meal.controller.transformer;

import dev.srello.cocinillas.meal.dto.DeleteMealIDTO;
import dev.srello.cocinillas.meal.dto.GetMealsIDTO;
import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.meal.rdto.GetMealsRQRDTO;
import dev.srello.cocinillas.meal.rdto.MealRQRDTO;
import dev.srello.cocinillas.meal.rdto.MealRSRDTO;
import dev.srello.cocinillas.recipe.controller.transformer.RecipeControllerMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = RecipeControllerMapper.class)
public interface MealControllerMapper {
    MealIDTO toMealIDTO(MealRQRDTO mealRQRDTO, Long userId);

    MealRSRDTO toMealRSRDTO(MealODTO mealODTO);

    List<MealRSRDTO> toMealsRSRDTO(List<MealODTO> mealODTO);

    GetMealsIDTO toGetMealsIDTO(GetMealsRQRDTO getMealsRQRDTO, Long userId);

    DeleteMealIDTO toDeleteMealIDTO(Long mealId, Long userId);
}
