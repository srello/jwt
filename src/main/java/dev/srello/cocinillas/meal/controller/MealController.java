package dev.srello.cocinillas.meal.controller;

import dev.srello.cocinillas.meal.controller.transformer.MealControllerTransformer;
import dev.srello.cocinillas.meal.dto.DeleteMealIDTO;
import dev.srello.cocinillas.meal.dto.GetMealsIDTO;
import dev.srello.cocinillas.meal.dto.MealIDTO;
import dev.srello.cocinillas.meal.dto.MealODTO;
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
        return ok().body(mealsRSRDTO);
    }

    @PostMapping
    public ResponseEntity<MealRSRDTO> createMeal(@RequestBody MealRQRDTO mealRQRDTO, @CurrentUser UserODTO currentUser) {
        MealIDTO mealIDTO = transformer.toMealIDTO(mealRQRDTO, currentUser.getId());
        MealODTO mealODTO = service.createMeal(mealIDTO);
        MealRSRDTO mealRSRDTO = transformer.toMealRSRDTO(mealODTO);
        return ok().body(mealRSRDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MealRSRDTO> deleteMeal(@PathVariable Long id, @CurrentUser UserODTO currentUser) {
        DeleteMealIDTO deleteMealIDTO = transformer.toDeleteMealIDTO(id, currentUser.getId());
        MealODTO mealODTO = service.deleteMeal(deleteMealIDTO);
        MealRSRDTO mealRSRDTO = transformer.toMealRSRDTO(mealODTO);
        return ok().body(mealRSRDTO);
    }
}
