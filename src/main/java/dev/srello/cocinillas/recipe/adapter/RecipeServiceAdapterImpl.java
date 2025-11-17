package dev.srello.cocinillas.recipe.adapter;

import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.recipe.adapter.transformer.RecipeServiceAdapterTransformer;
import dev.srello.cocinillas.recipe.dto.GetRecipesByIdIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecipeServiceAdapterImpl implements RecipeServiceAdapter {
    private final RecipeService recipeService;
    private final RecipeServiceAdapterTransformer transformer;


    @Override
    public List<RecipeODTO> getRecipesFromMenus(List<Menu> menus, Long userId) {
        List<Long> recipeIds = menus.stream()
                .flatMap(menu -> menu.getMenuMeals().parallelStream())
                .flatMap(meal -> meal.getRecipeIds().parallelStream())
                .distinct()
                .toList();

        return getRecipesById(recipeIds, userId);
    }

    @Override
    public List<RecipeODTO> getRecipesFromMeals(List<Meal> meals, Long userId) {
        List<Long> recipeIds = meals.stream()
                .flatMap(meal -> meal.getRecipeIds().parallelStream())
                .distinct()
                .toList();

        return getRecipesById(recipeIds, userId);
    }

    private List<RecipeODTO> getRecipesById(List<Long> recipeIds, Long userId) {
        GetRecipesByIdIDTO getRecipesByIdIDTO = transformer.toGetRecipesByIdIDTO(recipeIds, userId);
        return recipeService.getRecipesById(getRecipesByIdIDTO);
    }
}
