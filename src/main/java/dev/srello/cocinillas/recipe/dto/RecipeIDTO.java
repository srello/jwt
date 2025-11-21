package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.recipe.enums.RecipeVisibility;
import dev.srello.cocinillas.user.dto.UserODTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecipeIDTO {
    private String name;
    private String description;
    private List<IngredientIDTO> ingredientIds;
    private List<InstructionIDTO> instructions;
    private RecipeVisibility visibility;
    private List<Long> tagIds;
    private List<RecipeImageIDTO> images;
    private Integer totalDuration;
    private UserODTO author;
}
