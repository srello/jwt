package dev.srello.cocinillas.meal.adapter.transformer;

import dev.srello.cocinillas.meal.dto.GetMealsIDTO;
import dev.srello.cocinillas.shoppinglist.dto.RefreshShoppingListIDTO;
import dev.srello.cocinillas.shoppinglist.dto.ShoppingListIDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MealServiceAdapterTransformerImpl implements MealServiceAdapterTransformer {
    private final MealServiceAdapterMapper mapper;

    @Override
    public GetMealsIDTO toGetMealsIDTO(@NonNull ShoppingListIDTO shoppingListIDTO) {
        return mapper.toGetMealsIDTO(shoppingListIDTO);
    }

    @Override
    public GetMealsIDTO toGetMealsIDTO(@NonNull RefreshShoppingListIDTO refreshShoppingListIDTO) {
        return mapper.toGetMealsIDTO(refreshShoppingListIDTO);
    }
}
