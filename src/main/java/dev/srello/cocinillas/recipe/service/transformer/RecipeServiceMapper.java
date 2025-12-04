package dev.srello.cocinillas.recipe.service.transformer;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.product.service.transformer.ProductServiceMapper;
import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.model.Ingredient;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.model.RecipeInteraction;
import dev.srello.cocinillas.tags.model.Tag;
import dev.srello.cocinillas.tags.service.transformer.TagServiceMapper;
import dev.srello.cocinillas.user.model.User;
import dev.srello.cocinillas.user.service.transformer.UserServiceMapper;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.net.URL;
import java.util.List;
import java.util.stream.Collector;

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.PRODUCT_NOT_FOUND_CODE;
import static dev.srello.cocinillas.core.messages.Messages.Error.PRODUCT_NOT_FOUND;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Mapper(uses = {TagServiceMapper.class, ProductServiceMapper.class, UserServiceMapper.class})
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

    @Mapping(target = "id", source = "recipe.id")
    @Mapping(target = "name", source = "recipe.name")
    @Mapping(target = "macros", source = "recipe.ingredients", qualifiedByName = "getMacrosFromIngredients")
    RecipeODTO toRecipeODTO(Recipe recipe, List<URL> imageUrls, Boolean isLiked, Boolean isSaved, User author, @Context Long userId);

    RecipeInteraction toRecipeInteraction(RecipeInteractionIDTO recipeInteractionIDTO);

    RecipeInteractionODTO toRecipeInteractionODTO(RecipeInteraction recipeInteraction);

    @Mapping(target = "imageKeys", source = "recipeIDTO.images", qualifiedByName = "imagesToImageKeys")
    @Mapping(target = "ingredients", source = "recipeIDTO.ingredients", qualifiedByName = "buildRecipeIngredients")
    @Mapping(target = "likes", constant = "0L")
    Recipe toRecipe(RecipeIDTO recipeIDTO, List<Tag> tags, @Context List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "product")
    Ingredient toIngredient(IngredientIDTO ingredientIDTO, Product product);

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

    @Named("buildRecipeIngredients")
    default List<Ingredient> buildRecipeIngredients(List<IngredientIDTO> ingredientIDTOS, @Context List<Product> products) {
        return ingredientIDTOS.stream().map(ingredientIDTO ->
                        toIngredient(ingredientIDTO, getProduct(products, ingredientIDTO)))
                .toList();
    }

    private Product getProduct(List<Product> products, IngredientIDTO ingredientIDTO) {
        return products.stream()
                .filter(productODTO -> ingredientIDTO.getProductId().equals(productODTO.getId()))
                .findFirst()
                .orElseThrow(() -> new RequestException(NOT_FOUND, PRODUCT_NOT_FOUND, PRODUCT_NOT_FOUND_CODE));
    }

    @Named("imagesToImageKeys")
    default List<String> imagesToImageKeys(List<RecipeImageIDTO> images) {
        return images.stream().map(RecipeImageIDTO::getKey).toList();
    }
}

