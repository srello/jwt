package dev.srello.cocinillas.shoppinglist.service.transformer;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.product.service.transformer.ProductServiceMapper;
import dev.srello.cocinillas.recipe.dto.IngredientODTO;
import dev.srello.cocinillas.recipe.model.Ingredient;
import dev.srello.cocinillas.shoppinglist.dto.*;
import dev.srello.cocinillas.shoppinglist.model.ShoppingList;
import dev.srello.cocinillas.shoppinglist.model.ShoppingListItem;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.PRODUCT_NOT_FOUND_CODE;
import static dev.srello.cocinillas.core.messages.Messages.Error.PRODUCT_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Mapper(uses = ProductServiceMapper.class)
public interface ShoppingListServiceMapper {
    @Mapping(target = "author.id", source = "shoppingListIDTO.authorId")
    ShoppingList toShoppingList(ShoppingListIDTO shoppingListIDTO, List<ShoppingListItem> items);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "checked", constant = "false")
    ShoppingListItem toShoppingListItem(IngredientODTO ingredient);

    ShoppingListODTO toShoppingListODTO(ShoppingList shoppingList);

    ShoppingListSummaryODTO toShoppingListSummaryODTO(ShoppingList shoppingList);

    ShoppingListItemODTO toShoppingListItemODTO(ShoppingListItem savedShoppingListItem);

    @Mapping(target = "checked", constant = "false")
    @Mapping(target = "product", source = "item", qualifiedByName = "getProduct")
    ShoppingListItem toShoppingListItem(ShoppingListItemIDTO item, @Context List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "checked", constant = "false")
    ShoppingListItem toShoppingListItem(Ingredient ingredient);

    @Named("getProduct")
    default Product getProduct(ShoppingListItemIDTO item, @Context List<Product> products) {
        return products.stream()
                .filter(product -> product.getId().equals(item.getProductId()))
                .findFirst()
                .orElseThrow(() -> new RequestException(NOT_FOUND, PRODUCT_NOT_FOUND, PRODUCT_NOT_FOUND_CODE));

    }
}
