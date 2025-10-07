package dev.srello.cocinillas.recipe.controller.transformer;

import dev.srello.cocinillas.product.controller.transformer.ProductControllerMapper;
import dev.srello.cocinillas.recipe.dto.RecipeIDTO;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.rdto.RecipeRQRDTO;
import dev.srello.cocinillas.recipe.rdto.RecipeRSRDTO;
import dev.srello.cocinillas.recipe.rdto.RecipeSummaryRSRDTO;
import dev.srello.cocinillas.shared.pagination.transformer.PaginationMapper;
import dev.srello.cocinillas.tags.controller.transformer.TagControllerMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(uses = {PaginationMapper.class, TagControllerMapper.class, ProductControllerMapper.class})
public interface RecipeControllerMapper {
    RecipeIDTO toRecipeIDTO(RecipeRQRDTO recipeRQRDTO);

    RecipeRSRDTO toRecipeRSRDTO(RecipeODTO recipeODTO);

    @Mapping(target = "calories", source = "recipeODTO.macros.calories")
    RecipeSummaryRSRDTO toRecipeSummaryRSRDTO(RecipeODTO recipeODTO);

    default Page<RecipeSummaryRSRDTO> toRecipeSummaryRSRDTO(Page<RecipeODTO> recipesODTO) {
        return recipesODTO.map(this::toRecipeSummaryRSRDTO);
    }

    default Page<RecipeRSRDTO> toRecipesRSRDTO(Page<RecipeODTO> recipesODTO) {
        return recipesODTO.map(this::toRecipeRSRDTO);
    }
}
