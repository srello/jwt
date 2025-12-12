package dev.srello.cocinillas.meal.adapter;

import dev.srello.cocinillas.meal.adapter.transformer.MealServiceAdapterTransformer;
import dev.srello.cocinillas.meal.dto.GetMealsIDTO;
import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.meal.service.MealService;
import dev.srello.cocinillas.shoppinglist.dto.ShoppingListIDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MealServiceAdapterImpl implements MealServiceAdapter {
    private final MealService mealService;
    private final MealServiceAdapterTransformer transformer;

    @Override
    public List<Meal> getMealsFromShoppingList(ShoppingListIDTO shoppingListIDTO) {
        GetMealsIDTO getMealsIDTO = transformer.toGetMealsIDTO(shoppingListIDTO);
        return mealService.getModelMeals(getMealsIDTO);
    }
}
