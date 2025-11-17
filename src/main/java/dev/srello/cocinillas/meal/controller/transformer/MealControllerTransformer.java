package dev.srello.cocinillas.meal.controller.transformer;

import dev.srello.cocinillas.meal.dto.*;
import dev.srello.cocinillas.meal.rdto.DeleteMealsRQRDTO;
import dev.srello.cocinillas.meal.rdto.GetMealsRQRDTO;
import dev.srello.cocinillas.meal.rdto.MealRQRDTO;
import dev.srello.cocinillas.meal.rdto.MealRSRDTO;
import lombok.NonNull;

import java.util.List;

public interface MealControllerTransformer {
    MealIDTO toMealIDTO(@NonNull MealRQRDTO mealRQRDTO, @NonNull Long userId);

    List<MealIDTO> toMealsIDTO(@NonNull List<MealRQRDTO> mealRQRDTO, @NonNull Long userId);

    MealRSRDTO toMealRSRDTO(@NonNull MealODTO mealODTO);

    List<MealRSRDTO> toMealsRSRDTO(@NonNull List<MealODTO> mealODTO);

    GetMealsIDTO toGetMealsIDTO(@NonNull GetMealsRQRDTO getMealsRQRDTO, @NonNull Long userId);

    DeleteMealIDTO toDeleteMealIDTO(@NonNull Long mealId, @NonNull Long userId);

    DeleteMealsIDTO toDeleteMealsIDTO(@NonNull DeleteMealsRQRDTO deleteMealsRQRDTO, @NonNull Long userId);
}
