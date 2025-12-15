package dev.srello.cocinillas.shoppinglist.service;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.meal.adapter.MealServiceAdapter;
import dev.srello.cocinillas.meal.model.Meal;
import dev.srello.cocinillas.product.adapter.ProductServiceAdapter;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.recipe.adapter.RecipeServiceAdapter;
import dev.srello.cocinillas.recipe.model.Ingredient;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.shoppinglist.dto.*;
import dev.srello.cocinillas.shoppinglist.model.ShoppingList;
import dev.srello.cocinillas.shoppinglist.model.ShoppingListItem;
import dev.srello.cocinillas.shoppinglist.repository.ShoppingListItemRepository;
import dev.srello.cocinillas.shoppinglist.repository.ShoppingListRepository;
import dev.srello.cocinillas.shoppinglist.service.transformer.ShoppingListServiceTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.*;
import static dev.srello.cocinillas.core.messages.Messages.Error.*;
import static java.util.stream.Stream.concat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListServiceTransformer transformer;
    private final ShoppingListRepository repository;
    private final ShoppingListItemRepository shoppingListItemRepository;
    private final ProductServiceAdapter productServiceAdapter;
    private final MealServiceAdapter mealServiceAdapter;
    private final RecipeServiceAdapter recipeServiceAdapter;

    @Override
    public ShoppingListSummaryODTO createShoppingList(ShoppingListIDTO shoppingListIDTO) {
        List<Meal> meals = mealServiceAdapter.getMealsFromShoppingList(shoppingListIDTO);
        List<Ingredient> ingredients = recipeServiceAdapter.getIngredientsFromMeals(meals);
        ShoppingList shoppingList = transformer.toShoppingList(shoppingListIDTO, ingredients);
        ShoppingList savedShoppingList = repository.saveAndFlush(shoppingList);
        return transformer.toShoppingListSummaryODTO(savedShoppingList);
    }

    @Override
    public Page<ShoppingListSummaryODTO> getShoppingListsPaginated(Long id, PaginationIDTO paginationIDTO) {
        Page<ShoppingList> shoppingLists = repository.findAllByAuthorId(id, paginationIDTO.getPageRequest());
        return transformer.toShoppingListSummariesODTO(shoppingLists);
    }

    @Override
    public ShoppingListODTO getShoppingListById(GetShoppingListByIdIDTO getShoppingListByIdIDTO) {
        ShoppingList shoppingList = getShoppingListById(getShoppingListByIdIDTO.getShoppingListId());

        if (!shoppingList.getAuthor().getId().equals(getShoppingListByIdIDTO.getUserId()))
            throw new RequestException(BAD_REQUEST, RESOURCE_ACCESS_NOT_ALLOWED, RESOURCE_ACCESS_NOT_ALLOWED_CODE);

        return transformer.toShoppingListODTO(shoppingList);
    }

    @Override
    public ShoppingListODTO deleteShoppingList(DeleteShoppingListIDTO deleteShoppingListIDTO) {
        ShoppingList shoppingList = getShoppingListById(deleteShoppingListIDTO.getShoppingListId());

        if (!shoppingList.getAuthor().getId().equals(deleteShoppingListIDTO.getUserId()))
            throw new RequestException(BAD_REQUEST, RESOURCE_DELETION_NOT_ALLOWED, RESOURCE_DELETION_NOT_ALLOWED_CODE);

        repository.delete(shoppingList);

        return transformer.toShoppingListODTO(shoppingList);
    }

    @Override
    public ShoppingListItemODTO checkShoppingListItem(CheckItemIDTO checkItemIDTO) {
        ShoppingList shoppingList = getShoppingListById(checkItemIDTO.getId());

        if (!shoppingList.getAuthor().getId().equals(checkItemIDTO.getUserId()))
            throw new RequestException(BAD_REQUEST, RESOURCE_MODIFICATION_NOT_ALLOWED, RESOURCE_MODIFICATION_NOT_ALLOWED_CODE);

        ShoppingListItem shoppingListItem = shoppingList.getItems().stream()
                .filter(item -> item.getId().equals(checkItemIDTO.getItemId()))
                .findFirst()
                .orElseThrow(() -> new RequestException(NOT_FOUND, SHOPPING_LIST_ITEM_NOT_FOUND, SHOPPING_LIST_NOT_FOUND_CODE));

        shoppingListItem.setChecked(checkItemIDTO.getChecked());

        ShoppingListItem savedShoppingListItem = shoppingListItemRepository.saveAndFlush(shoppingListItem);

        return transformer.toShoppingListItemODTO(savedShoppingListItem);
    }

    @Override
    public List<ShoppingListItemODTO> addItemsToShoppingList(AddShoppingListItemsIDTO addShoppingListItemsIDTO) {
        ShoppingList shoppingList = getShoppingListById(addShoppingListItemsIDTO.getShoppingListId());
        if (!shoppingList.getAuthor().getId().equals(addShoppingListItemsIDTO.getUserId()))
            throw new RequestException(BAD_REQUEST, RESOURCE_MODIFICATION_NOT_ALLOWED, RESOURCE_MODIFICATION_NOT_ALLOWED_CODE);

        List<Product> products = productServiceAdapter.getProductsFromShoppingListItems(addShoppingListItemsIDTO.getItems());
        List<ShoppingListItem> newItems = transformer.toShoppingListItems(addShoppingListItemsIDTO.getItems(), products);
        shoppingList.getItems().addAll(newItems);
        repository.saveAndFlush(shoppingList);
        return transformer.toShoppingListItemsODTO(shoppingList.getItems());
    }

    @Override
    public ShoppingListItemODTO deleteShoppingListItem(DeleteShoppingListItemIDTO deleteShoppingListItemIDTO) {
        ShoppingList shoppingList = getShoppingListById(deleteShoppingListItemIDTO.getShoppingListId());

        if (!shoppingList.getAuthor().getId().equals(deleteShoppingListItemIDTO.getUserId()))
            throw new RequestException(BAD_REQUEST, RESOURCE_DELETION_NOT_ALLOWED, RESOURCE_DELETION_NOT_ALLOWED_CODE);

        ShoppingListItem shoppingListItem = shoppingList.getItems().stream()
                .filter(item -> item.getId().equals(deleteShoppingListItemIDTO.getItemId()))
                .findFirst()
                .orElseThrow(() -> new RequestException(NOT_FOUND, SHOPPING_LIST_ITEM_NOT_FOUND, SHOPPING_LIST_NOT_FOUND_CODE));

        shoppingListItemRepository.delete(shoppingListItem);
        return transformer.toShoppingListItemODTO(shoppingListItem);
    }

    @Override
    public ShoppingListODTO refreshShoppingList(RefreshShoppingListIDTO refreshShoppingListIDTO) {
        ShoppingList shoppingList = getShoppingListById(refreshShoppingListIDTO.getShoppingListId());

        if (!shoppingList.getAuthor().getId().equals(refreshShoppingListIDTO.getUserId()))
            throw new RequestException(BAD_REQUEST, RESOURCE_MODIFICATION_NOT_ALLOWED, RESOURCE_MODIFICATION_NOT_ALLOWED_CODE);

        List<Meal> meals = mealServiceAdapter.getMealsFromShoppingList(refreshShoppingListIDTO);
        List<Ingredient> ingredients = recipeServiceAdapter.getIngredientsFromMeals(meals);
        List<ShoppingListItem> generatedItems = transformer.toShoppingListItems(ingredients);
        List<ShoppingListItem> manuallyAddedItems = shoppingList.getItems().stream().filter(ShoppingListItem::getAddedManually).toList();
        shoppingList.setItems(concat(generatedItems.stream(), manuallyAddedItems.stream()).toList());
        ShoppingList savedShoppingList = repository.saveAndFlush(shoppingList);

        return transformer.toShoppingListODTO(savedShoppingList);
    }

    private ShoppingList getShoppingListById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RequestException(NOT_FOUND, SHOPPING_LIST_NOT_FOUND, SHOPPING_LIST_NOT_FOUND_CODE));
    }
}
