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
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

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
        List<Ingredient> ingredients = meals.stream()
                .map(this::getIngredientsFromMeal)
                .flatMap(Collection::stream)
                .toList();

        Map<Long, Ingredient> uniqueIngredients = ingredients.stream()
                .collect(toMap(
                        Ingredient::getId,
                        identity(),
                        (existing, replacement) -> existing
                ));

        return uniqueIngredients.values().stream()
                .map(ingredient -> sumIngredientQuantities(ingredient, ingredients))
                .toList();
    }

    private List<Ingredient> getIngredientsFromMeal(Meal meal) {
        List<Recipe> recipes = recipeService.getModelRecipesByIds(meal.getRecipeIds());

        return recipes.stream()
                .flatMap(recipe -> recipe.getIngredients().stream())
                .map(ingredient -> getIngredientPerDiners(ingredient, meal.getDiners()))
                .toList();
    }

    private Ingredient getIngredientPerDiners(Ingredient ingredient, Integer diners) {
        ingredient.setQuantity(ingredient.getQuantity() * diners);
        return ingredient;
    }

    private List<RecipeODTO> getRecipesById(List<Long> recipeIds, Long userId) {
        GetRecipesByIdIDTO getRecipesByIdIDTO = transformer.toGetRecipesByIdIDTO(recipeIds, userId);
        return recipeService.getRecipesById(getRecipesByIdIDTO);
    }
}
