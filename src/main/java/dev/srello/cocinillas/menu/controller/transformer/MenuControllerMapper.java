package dev.srello.cocinillas.menu.controller.transformer;

import dev.srello.cocinillas.menu.dto.*;
import dev.srello.cocinillas.menu.rdto.*;
import dev.srello.cocinillas.recipe.controller.transformer.RecipeControllerMapper;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.tags.controller.transformer.TagControllerMapper;
import dev.srello.cocinillas.user.dto.UserODTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.net.URL;
import java.util.Collection;
import java.util.List;

@Mapper(uses = {TagControllerMapper.class, RecipeControllerMapper.class})
public interface MenuControllerMapper {
    @Mapping(target = "name", source = "menu.name")
    MenuIDTO toMenuIDTO(MenuRQRDTO menu, UserODTO author);

    MenuRSRDTO toMenuRSRDTO(MenuODTO menuODTO);

    GetMenusIDTO toGetMenusIDTO(GetMenusRQRDTO getMenusRQRDTO, Long userId);

    @Mapping(target = "recipeImageUrls", source = "menuODTO", qualifiedByName = "getRecipeImageUrls")
    MenuSummaryRSRDTO toMenuSummaryRSRDTO(MenuODTO menuODTO);

    MenuInteractionIDTO toMenuInteractionIDTO(MenuInteractionRQRDTO menuInteractionRQRDTO, Long userId);

    MenuInteractionRSRDTO toMenuInteractionRSRDTO(MenuInteractionODTO menuInteractionODTO);

    GetMenuIDTO toGetMenuIDTO(Long menuId, Long userId);

    DeleteMenuIDTO toDeleteMenuIDTO(Long menuId, Long userId);

    @Named("getRecipeImageUrls")
    default List<String> getRecipeImageUrls(MenuODTO menuODTO) {
        return menuODTO.getMenuMeals().stream()
                .map(MenuMealODTO::getRecipes)
                .flatMap(Collection::stream)
                .distinct()
                .map(RecipeODTO::getImageUrls)
                .map(List::getFirst)
                .map(URL::toString)
                .toList();
    }
}
