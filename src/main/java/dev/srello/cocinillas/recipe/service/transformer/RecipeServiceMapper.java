package dev.srello.cocinillas.recipe.service.transformer;

import dev.srello.cocinillas.product.service.transformer.ProductServiceMapper;
import dev.srello.cocinillas.recipe.dto.RecipeODTO;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.tags.service.transformer.TagServiceMapper;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(uses = {TagServiceMapper.class, ProductServiceMapper.class})
public interface RecipeServiceMapper {
    RecipeODTO toRecipeODTO(Recipe recipe);

    default Page<RecipeODTO> toRecipesODTO(Page<Recipe> recipes) {
        return recipes.map(this::toRecipeODTO);
    }
}
