package dev.srello.cocinillas.recipe.adapter;

import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.recipe.adapter.transformer.RecipeServiceAdapterTransformer;
import dev.srello.cocinillas.recipe.dto.GetRecipesByIdIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Ingredient;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecipeServiceAdapterImpl implements RecipeServiceAdapter {
    private final RecipeService recipeService;
    private final RecipeServiceAdapterTransformer transformer;

    @NotNull
    private static Ingredient sumIngredientQuantities(Ingredient ingredient, List<Ingredient> ingredients) {
        Double quantity = ingredients.stream()
                .filter(i -> ingredient.getId().equals(i.getId()))
                .mapToDouble(Ingredient::getQuantity).sum();

        ingredient.setQuantity(quantity);
        return ingredient;
    }

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
        List<Long> recipeIds = meals.stream().map(Meal::getRecipeIds)
                .flatMap(Collection::parallelStream)
                .toList();

        return getRecipesById(recipeIds, userId);
    }

    @Override
    public List<Ingredient> getIngredientsFromMeals(List<Meal> meals) {
        List<Recipe> recipes = getRecipesFromMeals(meals);
        List<Ingredient> ingredients = recipes.stream().map(Recipe::getIngredients)
                .flatMap(Collection::stream)
                .toList();

        return ingredients.stream().distinct()
                .map(ingredient -> sumIngredientQuantities(ingredient, ingredients))
                .toList();
    }

    private List<Recipe> getRecipesFromMeals(List<Meal> meals) {
        List<Long> recipeIds = meals.stream().map(Meal::getRecipeIds)
                .flatMap(Collection::stream)
                .toList();
        return recipeService.getModelRecipesByIds(recipeIds);
    }

    private List<RecipeODTO> getRecipesById(List<Long> recipeIds, Long userId) {
        GetRecipesByIdIDTO getRecipesByIdIDTO = transformer.toGetRecipesByIdIDTO(recipeIds, userId);
        return recipeService.getRecipesById(getRecipesByIdIDTO);
    }
}
