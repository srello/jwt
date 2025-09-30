package dev.srello.cocinillas.recipe.controller;

import dev.srello.cocinillas.recipe.controller.transformer.RecipeControllerTransformer;
import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.rdto.RecipeRQRDTO;
import dev.srello.cocinillas.recipe.rdto.RecipeSummaryRSRDTO;
import dev.srello.cocinillas.recipe.service.RecipeService;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.shared.pagination.rdto.PaginationRQRDTO;
import dev.srello.cocinillas.shared.pagination.transformer.PaginationTransformer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static dev.srello.cocinillas.recipe.controller.RecipeController.RECIPE_ROUTE;

@Controller
@RequiredArgsConstructor
@RequestMapping(RECIPE_ROUTE)
public class RecipeController {
    public static final String RECIPE_ROUTE = "/recipes";
    private final RecipeService service;
    private final RecipeControllerTransformer transformer;
    private final PaginationTransformer paginationTransformer;

    @GetMapping
    public ResponseEntity<Page<RecipeSummaryRSRDTO>> getRecipesPaginated(@Valid RecipeRQRDTO recipeRQRDTO, @Valid PaginationRQRDTO paginationRQRDTO) {
        RecipeIDTO recipeIDTO = transformer.toRecipeIDTO(recipeRQRDTO);
        PaginationIDTO paginationIDTO = paginationTransformer.toPaginationIDTO(paginationRQRDTO);
        Page<RecipeODTO> recipesSummariesODTO = service.getRecipesPaginated(recipeIDTO, paginationIDTO);
        Page<RecipeSummaryRSRDTO> recipeSummaryRSRDTO = transformer.toRecipeSummaryRSRDTO(recipesSummariesODTO);

        return ResponseEntity.ok().body(recipeSummaryRSRDTO);
    }
}
