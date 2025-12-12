package dev.srello.cocinillas.meal.adapter.transformer;

import dev.srello.cocinillas.meal.dto.GetMealsIDTO;
import dev.srello.cocinillas.shoppinglist.dto.ShoppingListIDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MealServiceAdapterMapper {
    @Mapping(target = "startDateTime", source = "startDate")
    @Mapping(target = "endDateTime", source = "endDate")
    @Mapping(target = "userId", source = "authorId")
    GetMealsIDTO toGetMealsIDTO(ShoppingListIDTO shoppingListIDTO);
}
