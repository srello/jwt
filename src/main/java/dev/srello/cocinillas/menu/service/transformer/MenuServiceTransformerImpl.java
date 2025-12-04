package dev.srello.cocinillas.menu.service.transformer;

import dev.srello.cocinillas.menu.dto.*;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.menu.model.MenuInteraction;
import dev.srello.cocinillas.menu.model.MenuMeal;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

import static dev.srello.cocinillas.shared.enums.InteractionType.LIKE;
import static dev.srello.cocinillas.shared.enums.InteractionType.SAVE;


@Component
@RequiredArgsConstructor
public class MenuServiceTransformerImpl implements MenuServiceTransformer {
    private final MenuServiceMapper mapper;

    @Override
    public Menu toMenu(@NonNull MenuIDTO menuIDTO) {
        return mapper.toMenu(menuIDTO);
    }

    @Override
    public MenuODTO toMenuODTO(@NonNull Menu menu, @NonNull List<MenuInteraction> menuInteractions, @NonNull List<RecipeODTO> recipes, @NonNull Long userId) {
        List<MenuInteraction> filteredInteractions = menuInteractions.stream()
                .filter(menuInteraction -> menuInteraction.getMenuId().equals(menu.getId()))
                .toList();
        Boolean isLiked = filteredInteractions.stream().anyMatch(menuInteraction -> menuInteraction.getType().equals(LIKE));
        Boolean isSaved = filteredInteractions.stream().anyMatch(menuInteraction -> menuInteraction.getType().equals(SAVE));
        List<MenuMealODTO> menuMeals = menu.getMenuMeals().stream().map(menuMeal -> toMenuMealODTO(menuMeal, recipes)).toList();
        return mapper.toMenuODTO(menu, isLiked, isSaved, menuMeals, menu.getAuthor(), userId);
    }

    @Override
    public Page<MenuODTO> toMenusODTO(@NonNull Page<Menu> menus, @NonNull List<MenuInteraction> menuInteractions, @NonNull List<RecipeODTO> recipes, @NonNull Long userId) {

        return menus.map(menu -> toMenuODTO(menu, menuInteractions, recipes, userId));
    }

    @Override
    public MenuInteraction toMenuInteraction(@NonNull MenuInteractionIDTO menuInteractionIDTO) {
        return mapper.toMenuInteraction(menuInteractionIDTO);
    }

    @Override
    public MenuInteractionODTO toMenuInteractionODTO(@NonNull MenuInteraction menuInteraction) {
        return mapper.toMenuInteractionODTO(menuInteraction);
    }

    private MenuMealODTO toMenuMealODTO(@NonNull MenuMeal menuMeal, @NonNull List<RecipeODTO> recipes) {
        List<RecipeODTO> menuMealRecipes = recipes.stream()
                .filter(recipe -> menuMeal.getRecipeIds().contains(recipe.getId()))
                .toList();

        return mapper.toMenuMealODTO(menuMeal, menuMealRecipes);
    }
}
