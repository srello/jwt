package dev.srello.cocinillas.recipe.controller;

import dev.srello.cocinillas.recipe.controller.transformer.RecipeControllerTransformer;
import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.rdto.*;
import dev.srello.cocinillas.recipe.service.RecipeService;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.shared.pagination.rdto.PaginationRQRDTO;
import dev.srello.cocinillas.shared.pagination.transformer.PaginationTransformer;
import dev.srello.cocinillas.user.CurrentUser;
import dev.srello.cocinillas.user.dto.UserODTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static dev.srello.cocinillas.core.request.RequestConstants.*;
import static dev.srello.cocinillas.recipe.controller.RecipeController.RECIPE_ROUTE;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequiredArgsConstructor
@RequestMapping(RECIPE_ROUTE)
public class RecipeController {
    public static final String RECIPE_ROUTE = "/recipes";

    private final RecipeService service;
    private final RecipeControllerTransformer transformer;
    private final PaginationTransformer paginationTransformer;

    @GetMapping(PUBLIC_ENDPOINT)
    public ResponseEntity<Page<RecipeSummaryRSRDTO>> getRecipesPaginated(@Valid GetRecipesRQRDTO getRecipesRQRDTO, @Valid PaginationRQRDTO paginationRQRDTO) {
        GetRecipesIDTO getRecipesIDTO = transformer.toGetRecipesIDTO(getRecipesRQRDTO);
        PaginationIDTO paginationIDTO = paginationTransformer.toPaginationIDTO(paginationRQRDTO);
        Page<RecipeODTO> recipesSummariesODTO = service.getRecipesPaginated(getRecipesIDTO, paginationIDTO);
        Page<RecipeSummaryRSRDTO> recipeSummaryRSRDTO = transformer.toRecipeSummaryRSRDTO(recipesSummariesODTO);

        return ok(recipeSummaryRSRDTO);
    }

    @GetMapping(ME_PATH_VARIABLE)
    public ResponseEntity<Page<RecipeSummaryRSRDTO>> getUserRecipesPaginated(@Valid GetUserRecipesRQRDTO getUserRecipesRQRDTO, @Valid PaginationRQRDTO paginationRQRDTO, @CurrentUser UserODTO currentUser) {
        GetRecipesIDTO getRecipesIDTO = transformer.toGetRecipesIDTO(getUserRecipesRQRDTO, currentUser.getId());
        PaginationIDTO paginationIDTO = paginationTransformer.toPaginationIDTO(paginationRQRDTO);
        Page<RecipeODTO> recipesODTO = service.getUserRecipesPaginated(getRecipesIDTO, paginationIDTO);
        Page<RecipeSummaryRSRDTO> recipeSummaryRSRDTO = transformer.toRecipeSummaryRSRDTO(recipesODTO);

        return ok(recipeSummaryRSRDTO);
    }

    @GetMapping(PUBLIC_ENDPOINT + ID_PATH_VARIABLE)
    public ResponseEntity<RecipeRSRDTO> getRecipeById(@PathVariable Long id, @RequestParam(required = false) Long userId) {
        GetRecipeIDTO getRecipeIDTO = transformer.toGetRecipeIDTO(id, userId);
        RecipeODTO recipeODTO = service.getRecipeById(getRecipeIDTO);
        RecipeRSRDTO recipeRSRDTO = transformer.toRecipeRSRDTO(recipeODTO);

        return ok(recipeRSRDTO);
    }

    @PostMapping
    public ResponseEntity<RecipeRSRDTO> createRecipe(@RequestBody @Valid RecipeRQRDTO recipeRQRDTO, @CurrentUser UserODTO userODTO) {
        RecipeIDTO recipeIDTO = transformer.toRecipeIDTO(recipeRQRDTO, userODTO);
        RecipeODTO recipeODTO = service.createRecipe(recipeIDTO);
        RecipeRSRDTO recipeRSRDTO = transformer.toRecipeRSRDTO(recipeODTO);
        return ok(recipeRSRDTO);
    }


    @PutMapping(ID_PATH_VARIABLE)
    public ResponseEntity<RecipeRSRDTO> editRecipeById(@PathVariable Long id, @RequestBody @Valid RecipeRQRDTO recipeRQRDTO, @CurrentUser UserODTO currentUser) {
        EditRecipeIDTO editRecipeIDTO = transformer.toEditRecipeIDTO(id, recipeRQRDTO, currentUser);
        RecipeODTO recipeODTO = service.editRecipeById(editRecipeIDTO);
        RecipeRSRDTO recipeRSRDTO = transformer.toRecipeRSRDTO(recipeODTO);

        return ok(recipeRSRDTO);
    }

    @DeleteMapping(ID_PATH_VARIABLE)
    public ResponseEntity<RecipeRSRDTO> deleteRecipeById(@PathVariable Long id, @CurrentUser UserODTO currentUser) {
        DeleteRecipeIDTO deleteRecipeIDTO = transformer.toDeleteRecipeIDTO(id, currentUser);
        RecipeODTO recipeODTO = service.deleteRecipeById(deleteRecipeIDTO);
        RecipeRSRDTO recipeRSRDTO = transformer.toRecipeRSRDTO(recipeODTO);

        return ok(recipeRSRDTO);
    }

    @PostMapping(INTERACTIONS_PATH)
    public ResponseEntity<RecipeInteractionRSRDTO> createRecipeInteraction(@RequestBody RecipeInteractionRQRDTO recipeInteractionRQRDTO, @CurrentUser UserODTO currentUser) {
        RecipeInteractionIDTO recipeInteractionIDTO = transformer.toRecipeInteractionIDTO(recipeInteractionRQRDTO, currentUser.getId());
        RecipeInteractionODTO recipeInteractionODTO = service.createRecipeInteraction(recipeInteractionIDTO);
        RecipeInteractionRSRDTO recipeInteractionRSRDTO = transformer.toRecipeInteractionRSRDTO(recipeInteractionODTO);
        return ok(recipeInteractionRSRDTO);
    }

    @DeleteMapping(INTERACTIONS_PATH)
    public ResponseEntity<RecipeInteractionRSRDTO> deleteRecipeInteraction(@Valid RecipeInteractionRQRDTO recipeInteractionRQRDTO, @CurrentUser UserODTO currentUser) {
        RecipeInteractionIDTO recipeInteractionIDTO = transformer.toRecipeInteractionIDTO(recipeInteractionRQRDTO, currentUser.getId());
        RecipeInteractionODTO recipeInteractionODTO = service.deleteRecipeInteraction(recipeInteractionIDTO);
        RecipeInteractionRSRDTO recipeInteractionRSRDTO = transformer.toRecipeInteractionRSRDTO(recipeInteractionODTO);
        return ok(recipeInteractionRSRDTO);
    }
}
