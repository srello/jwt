package dev.srello.cocinillas.meal.controller;

import dev.srello.cocinillas.meal.controller.transformer.MealControllerTransformer;
import dev.srello.cocinillas.meal.dto.*;
import dev.srello.cocinillas.meal.rdto.DeleteMealsRQRDTO;
import dev.srello.cocinillas.meal.rdto.GetMealsRQRDTO;
import dev.srello.cocinillas.meal.rdto.MealRQRDTO;
import dev.srello.cocinillas.meal.rdto.MealRSRDTO;
import dev.srello.cocinillas.meal.service.MealService;
import dev.srello.cocinillas.user.CurrentUser;
import dev.srello.cocinillas.user.dto.UserODTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.srello.cocinillas.core.request.RequestConstants.ID_PATH_VARIABLE;
import static dev.srello.cocinillas.core.request.RequestConstants.MULTIPLE_PATH_VARIABLE;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequiredArgsConstructor
@RequestMapping("/meals")
public class MealController {
    private final MealControllerTransformer transformer;
    private final MealService service;

    @GetMapping
    public ResponseEntity<List<MealRSRDTO>> getMeals(@Valid GetMealsRQRDTO getMealsRQRDTO, @CurrentUser UserODTO currentUser) {
        GetMealsIDTO getMealsIDTO = transformer.toGetMealsIDTO(getMealsRQRDTO, currentUser.getId());
        List<MealODTO> mealsODTO = service.getMeals(getMealsIDTO);
        List<MealRSRDTO> mealsRSRDTO = transformer.toMealsRSRDTO(mealsODTO);
        return ok(mealsRSRDTO);
    }

    @PostMapping
    public ResponseEntity<MealRSRDTO> createMeal(@RequestBody MealRQRDTO mealRQRDTO, @CurrentUser UserODTO currentUser) {
        MealIDTO mealIDTO = transformer.toMealIDTO(mealRQRDTO, currentUser.getId());
        MealODTO mealODTO = service.createMeal(mealIDTO);
        MealRSRDTO mealRSRDTO = transformer.toMealRSRDTO(mealODTO);
        return ok(mealRSRDTO);
    }

    @PostMapping(MULTIPLE_PATH_VARIABLE)
    public ResponseEntity<List<MealRSRDTO>> createMeals(@RequestBody List<MealRQRDTO> mealRQRDTO, @CurrentUser UserODTO currentUser) {
        List<MealIDTO> mealIDTO = transformer.toMealsIDTO(mealRQRDTO, currentUser.getId());
        List<MealODTO> mealsODTO = service.createMeals(mealIDTO);
        List<MealRSRDTO> mealsRSRDTO = transformer.toMealsRSRDTO(mealsODTO);
        return ok(mealsRSRDTO);
    }

    @DeleteMapping(ID_PATH_VARIABLE)
    public ResponseEntity<MealRSRDTO> deleteMeal(@PathVariable Long id, @CurrentUser UserODTO currentUser) {
        DeleteMealIDTO deleteMealIDTO = transformer.toDeleteMealIDTO(id, currentUser.getId());
        MealODTO mealODTO = service.deleteMeal(deleteMealIDTO);
        MealRSRDTO mealRSRDTO = transformer.toMealRSRDTO(mealODTO);
        return ok(mealRSRDTO);
    }

    @DeleteMapping(MULTIPLE_PATH_VARIABLE)
    public ResponseEntity<List<MealRSRDTO>> deleteMeals(@Valid DeleteMealsRQRDTO deleteMealsRQRDTO, @CurrentUser UserODTO currentUser) {
        DeleteMealsIDTO deleteMealsIDTO = transformer.toDeleteMealsIDTO(deleteMealsRQRDTO, currentUser.getId());
        List<MealODTO> mealsODTO = service.deleteMeals(deleteMealsIDTO);
        List<MealRSRDTO> mealsRSRDTO = transformer.toMealsRSRDTO(mealsODTO);
        return ok(mealsRSRDTO);
    }

}
