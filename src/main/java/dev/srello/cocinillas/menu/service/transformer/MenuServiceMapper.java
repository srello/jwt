package dev.srello.cocinillas.menu.service.transformer;

import dev.srello.cocinillas.menu.dto.MenuIDTO;
import dev.srello.cocinillas.menu.dto.MenuMealODTO;
import dev.srello.cocinillas.menu.dto.MenuODTO;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.menu.model.MenuMeal;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.tags.service.transformer.TagServiceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = TagServiceMapper.class)
public interface MenuServiceMapper {
    Menu toMenu(MenuIDTO menuIDTO);

    @Mapping(target = "menuMeals", source = "menuMeals")
    MenuODTO toMenuODTO(Menu menu, Boolean isLiked, Boolean isSaved, List<MenuMealODTO> menuMeals);

    MenuMealODTO toMenuMealODTO(MenuMeal menuMeal, List<RecipeODTO> recipes);
}
