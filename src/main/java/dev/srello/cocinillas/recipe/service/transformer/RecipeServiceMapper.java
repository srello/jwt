package dev.srello.cocinillas.recipe.service.transformer;

import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.product.service.transformer.ProductServiceMapper;
import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.model.Ingredient;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.model.RecipeInteraction;
import dev.srello.cocinillas.tags.service.transformer.TagServiceMapper;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.net.URL;
import java.util.List;
import java.util.stream.Collector;

import static java.util.Optional.ofNullable;

@Mapper(uses = {TagServiceMapper.class, ProductServiceMapper.class})
public interface RecipeServiceMapper {
    Double CONVERSION_MACROS_MULTIPLIER = 0.01;

    private static double calculateMacro(Double quantity, Double macro) {
        return quantity * CONVERSION_MACROS_MULTIPLIER * macro;
    }

    @NotNull
    private static MacrosODTO mergeMacros(MacrosODTO accumulator1, MacrosODTO accumulator2) {
        accumulator1.setCalories(ofNullable(accumulator1.getCalories()).orElse(0D) + ofNullable(accumulator2.getCalories()).orElse(0D));
        accumulator1.setFat(ofNullable(accumulator1.getFat()).orElse(0D) + ofNullable(accumulator2.getFat()).orElse(0D));
        accumulator1.setProtein(ofNullable(accumulator1.getProtein()).orElse(0D) + ofNullable(accumulator2.getProtein()).orElse(0D));
        accumulator1.setCarbohydrates(ofNullable(accumulator1.getCarbohydrates()).orElse(0D) + ofNullable(accumulator2.getCarbohydrates()).orElse(0D));
        return accumulator1;
    }

    @Mapping(target = "macros", source = "recipe.ingredients", qualifiedByName = "getMacrosFromIngredients")
    @Mapping(target = "author", source = "author")
    RecipeODTO toRecipeODTO(Recipe recipe, List<URL> imageUrls, Boolean isLiked, Boolean isSaved, RecipeAuthorODTO author);

    RecipeAuthorODTO toRecipeAuthorODTO(String username, Boolean isUserAuthor);

    RecipeInteraction toRecipeInteraction(RecipeInteractionIDTO recipeInteractionIDTO);

    RecipeInteractionODTO toRecipeInteractionODTO(RecipeInteraction recipeInteraction);

    @Mapping(target = "imageKeys", source = "images", qualifiedByName = "imagesToImageKeys")
    Recipe toRecipe(RecipeIDTO recipeIDTO);

    @Mapping(target = "product", source = "productId", qualifiedByName = "productIdToProduct")
    Ingredient toIngredient(IngredientIDTO ingredientIDTO);

    @Named("getMacrosFromIngredients")
    default MacrosODTO getMacrosFromIngredients(List<Ingredient> ingredients) {
        return ingredients.parallelStream().map(ingredient -> {
                    Double quantity = ingredient.getQuantity();
                    Product product = ingredient.getProduct();
                    return MacrosODTO.builder()
                            .calories(calculateMacro(quantity, product.getCalories()))
                            .fat(calculateMacro(quantity, product.getFat()))
                            .protein(calculateMacro(quantity, product.getProtein()))
                            .carbohydrates(calculateMacro(quantity, product.getCarbohydrates()))
                            .build();
                })
                .collect(Collector.of(MacrosODTO::new, RecipeServiceMapper::mergeMacros, RecipeServiceMapper::mergeMacros)
                );
    }

    @Named("productIdToProduct")
    default Product productIdToProduct(Long productId) {
        return Product.builder()
                .id(productId)
                .build();
    }

    @Named("imagesToImageKeys")
    default List<String> imagesToImageKeys(List<RecipeImageIDTO> images) {
        return images.stream().map(RecipeImageIDTO::getKey).toList();
    }
}

