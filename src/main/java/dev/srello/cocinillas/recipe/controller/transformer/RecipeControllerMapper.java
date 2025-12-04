package dev.srello.cocinillas.recipe.controller.transformer;

import dev.srello.cocinillas.product.controller.transformer.ProductControllerMapper;
import dev.srello.cocinillas.recipe.dto.*;
import dev.srello.cocinillas.recipe.rdto.*;
import dev.srello.cocinillas.shared.pagination.transformer.PaginationMapper;
import dev.srello.cocinillas.tags.controller.transformer.TagControllerMapper;
import dev.srello.cocinillas.user.dto.UserODTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;

@Mapper(uses = {PaginationMapper.class, TagControllerMapper.class, ProductControllerMapper.class})
public interface RecipeControllerMapper {
    GetRecipesIDTO toGetRecipesIDTO(GetRecipesRQRDTO getRecipesRQRDTO);

    GetRecipesIDTO toGetRecipesIDTO(GetUserRecipesRQRDTO getUserRecipesRQRDTO, Long userId);

    RecipeRSRDTO toRecipeRSRDTO(RecipeODTO recipeODTO);

    RecipeSummaryRSRDTO toRecipeSummaryRSRDTO(RecipeODTO recipeODTO);

    GetRecipeIDTO toGetRecipeIDTO(Long id, Long userId);

    DeleteRecipeIDTO toDeleteRecipeIDTO(Long recipeId, UserODTO user);

    RecipeInteractionIDTO toRecipeInteractionsIDTO(RecipeInteractionRQRDTO recipeInteractionRQRDTO, Long userId);

    RecipeInteractionRSRDTO toRecipeInteractionsRSRDTO(RecipeInteractionODTO recipeInteractionODTO);

    @Mapping(target = "images", source = "recipeRQRDTO", qualifiedByName = "toRecipeImageIDTO")
    @Mapping(target = "name", source = "recipeRQRDTO.name")
    RecipeIDTO toRecipeIDTO(RecipeRQRDTO recipeRQRDTO, UserODTO author);


    EditRecipeIDTO toEditRecipeIDTO(Long id, RecipeIDTO recipeIDTO);

    default Page<RecipeSummaryRSRDTO> toRecipeSummaryRSRDTO(Page<RecipeODTO> recipesODTO) {
        return recipesODTO.map(this::toRecipeSummaryRSRDTO);
    }

    @Named("toRecipeImageIDTO")
    default List<RecipeImageIDTO> toRecipeImageIDTO(RecipeRQRDTO recipeRQRDTO) {
        return recipeRQRDTO.imageContentTypes().stream().map(imageContentType -> {
            String key = format("%s_%s", recipeRQRDTO.name(), randomUUID());
            return new RecipeImageIDTO(key, imageContentType);
        }).toList();
    }

}
