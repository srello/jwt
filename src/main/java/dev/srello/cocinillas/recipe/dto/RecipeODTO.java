package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.product.dto.ProductODTO;
import dev.srello.cocinillas.recipe.enums.RecipeVisibility;
import dev.srello.cocinillas.tags.dto.TagODTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecipeODTO {

    private Long id;
    private String name;
    private List<ProductODTO> ingredients;
    private RecipeVisibility visibility;
    private List<TagODTO> tags;
    private List<String> imageUrls;
}
