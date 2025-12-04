package dev.srello.cocinillas.product.adapter;

import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.product.service.ProductService;
import dev.srello.cocinillas.recipe.dto.IngredientIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductServiceAdapterImpl implements ProductServiceAdapter {
    private final ProductService productService;

    @Override
    public List<Product> getProductsFromRecipeIDTO(RecipeIDTO recipeIDTO) {
        List<Long> productIds = recipeIDTO.getIngredients().stream().map(IngredientIDTO::getProductId).toList();
        return productService.getProductsByIds(productIds, recipeIDTO.getAuthor().getId());
    }
}
