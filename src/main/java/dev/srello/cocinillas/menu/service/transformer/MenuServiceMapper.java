package dev.srello.cocinillas.menu.service.transformer;

import dev.srello.cocinillas.menu.dto.*;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.menu.model.MenuInteraction;
import dev.srello.cocinillas.menu.model.MenuMeal;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.tags.service.transformer.TagServiceMapper;
import dev.srello.cocinillas.user.model.User;
import dev.srello.cocinillas.user.service.transformer.UserServiceMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {TagServiceMapper.class, UserServiceMapper.class})
public interface MenuServiceMapper {
    Menu toMenu(MenuIDTO menuIDTO);

    @Mapping(target = "menuMeals", source = "menuMeals")
    @Mapping(target = "id", source = "menu.id")
    @Mapping(target = "name", source = "menu.name")
    MenuODTO toMenuODTO(Menu menu, Boolean isLiked, Boolean isSaved, List<MenuMealODTO> menuMeals, User author, @Context Long userId);

    MenuMealODTO toMenuMealODTO(MenuMeal menuMeal, List<RecipeODTO> recipes);

    MenuInteraction toMenuInteraction(MenuInteractionIDTO menuInteractionIDTO);

    MenuInteractionODTO toMenuInteractionODTO(MenuInteraction menuInteraction);
}
