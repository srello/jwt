package dev.srello.cocinillas.recipe.controller.transformer;

import dev.srello.cocinillas.product.controller.transformer.ProductControllerMapper;
import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.rdto.*;
import dev.srello.cocinillas.shared.pagination.transformer.PaginationMapper;
import dev.srello.cocinillas.tags.controller.transformer.TagControllerMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(uses = {PaginationMapper.class, TagControllerMapper.class, ProductControllerMapper.class})
public interface RecipeControllerMapper {
    GetRecipesIDTO toGetRecipesIDTO(GetRecipesRQRDTO getRecipesRQRDTO);

    RecipeRSRDTO toRecipeRSRDTO(RecipeODTO recipeODTO);

    @Mapping(target = "calories", source = "recipeODTO.macros.calories")
    RecipeSummaryRSRDTO toRecipeSummaryRSRDTO(RecipeODTO recipeODTO);

    GetRecipeIDTO toGetRecipeIDTO(Long id, Long userId);

    RecipeInteractionIDTO toRecipeInteractionsIDTO(RecipeInteractionRQRDTO recipeInteractionRQRDTO, Long userId);

    RecipeInteractionRSRDTO toRecipeInteractionsRSRDTO(RecipeInteractionODTO recipeInteractionODTO);

    default Page<RecipeSummaryRSRDTO> toRecipeSummaryRSRDTO(Page<RecipeODTO> recipesODTO) {
        return recipesODTO.map(this::toRecipeSummaryRSRDTO);
    }

    default Page<RecipeRSRDTO> toRecipesRSRDTO(Page<RecipeODTO> recipesODTO) {
        return recipesODTO.map(this::toRecipeRSRDTO);
    }
}
