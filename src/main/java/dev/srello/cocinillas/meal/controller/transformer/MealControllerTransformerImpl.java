package dev.srello.cocinillas.meal.controller.transformer;

import dev.srello.cocinillas.meal.dto.DeleteMealIDTO;
import dev.srello.cocinillas.meal.dto.GetMealsIDTO;
import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.meal.rdto.GetMealsRQRDTO;
import dev.srello.cocinillas.meal.rdto.MealRQRDTO;
import dev.srello.cocinillas.meal.rdto.MealRSRDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MealControllerTransformerImpl implements MealControllerTransformer {

    private final MealControllerMapper mapper;

    @Override
    public MealIDTO toMealIDTO(MealRQRDTO mealRQRDTO, Long userId) {
        return mapper.toMealIDTO(mealRQRDTO, userId);
    }

    @Override
    public MealRSRDTO toMealRSRDTO(MealODTO mealODTO) {
        return mapper.toMealRSRDTO(mealODTO);
    }

    @Override
    public List<MealRSRDTO> toMealsRSRDTO(List<MealODTO> mealODTO) {
        return mapper.toMealsRSRDTO(mealODTO);
    }

    @Override
    public GetMealsIDTO toGetMealsIDTO(GetMealsRQRDTO getMealsRQRDTO, Long userId) {
        return mapper.toGetMealsIDTO(getMealsRQRDTO, userId);
    }

    @Override
    public DeleteMealIDTO toDeleteMealIDTO(Long mealId, Long userId) {
        return mapper.toDeleteMealIDTO(mealId, userId);
    }
}
