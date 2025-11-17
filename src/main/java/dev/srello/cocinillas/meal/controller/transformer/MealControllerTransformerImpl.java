package dev.srello.cocinillas.meal.controller.transformer;

import dev.srello.cocinillas.meal.dto.*;
import dev.srello.cocinillas.meal.rdto.DeleteMealsRQRDTO;
import dev.srello.cocinillas.meal.rdto.GetMealsRQRDTO;
import dev.srello.cocinillas.meal.rdto.MealRQRDTO;
import dev.srello.cocinillas.meal.rdto.MealRSRDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MealControllerTransformerImpl implements MealControllerTransformer {

    private final MealControllerMapper mapper;

    @Override
    public MealIDTO toMealIDTO(@NotNull MealRQRDTO mealRQRDTO, @NotNull Long userId) {
        return mapper.toMealIDTO(mealRQRDTO, userId);
    }

    @Override
    public List<MealIDTO> toMealsIDTO(@NonNull List<MealRQRDTO> meals, @NotNull Long userId) {
        return meals.stream()
                .map(meal -> toMealIDTO(meal, userId))
                .toList();
    }

    @Override
    public MealRSRDTO toMealRSRDTO(@NotNull MealODTO mealODTO) {
        return mapper.toMealRSRDTO(mealODTO);
    }

    @Override
    public List<MealRSRDTO> toMealsRSRDTO(@NotNull List<MealODTO> mealODTO) {
        return mapper.toMealsRSRDTO(mealODTO);
    }

    @Override
    public GetMealsIDTO toGetMealsIDTO(@NotNull GetMealsRQRDTO getMealsRQRDTO, @NotNull Long userId) {
        return mapper.toGetMealsIDTO(getMealsRQRDTO, userId);
    }

    @Override
    public DeleteMealIDTO toDeleteMealIDTO(@NotNull Long mealId, @NotNull Long userId) {
        return mapper.toDeleteMealIDTO(mealId, userId);
    }

    @Override
    public DeleteMealsIDTO toDeleteMealsIDTO(@NonNull DeleteMealsRQRDTO deleteMealsRQRDTO, @NonNull Long userId) {
        return mapper.toDeleteMealsIDTO(deleteMealsRQRDTO, userId);
    }
}
