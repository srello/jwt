package dev.srello.cocinillas.product.adapter;

import dev.srello.cocinillas.meal.dto.MealODTO;
import dev.srello.cocinillas.product.dto.ProductODTO;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.product.service.ProductService;
import dev.srello.cocinillas.recipe.dto.IngredientIDTO;
import dev.srello.cocinillas.recipe.dto.IngredientODTO;
import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.shoppinglist.dto.ShoppingListItemIDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductServiceAdapterImpl implements ProductServiceAdapter {
    private final ProductService productService;

    @Override
    public List<Product> getProductsFromRecipeIDTO(RecipeIDTO recipeIDTO) {
        List<Long> productIds = recipeIDTO.getIngredients().stream().map(IngredientIDTO::getProductId).toList();
        return productService.getProductsByIds(productIds);
    }

    @Override
    public List<Product> getProductsFromShoppingListItems(List<ShoppingListItemIDTO> shoppingListItems) {
        List<Long> productIds = shoppingListItems.stream().map(ShoppingListItemIDTO::getProductId).toList();
        return productService.getProductsByIds(productIds);
    }

    @Override
    public List<Product> getProductsFromMeals(List<MealODTO> meals) {
        List<Long> productIds = meals.stream()
                .map(MealODTO::getRecipes)
                .flatMap(Collection::stream)
                .map(RecipeODTO::getIngredients)
                .flatMap(Collection::stream)
                .map(IngredientODTO::getProduct)
                .map(ProductODTO::getId)
                .toList();
        return productService.getProductsByIds(productIds);
    }
}
