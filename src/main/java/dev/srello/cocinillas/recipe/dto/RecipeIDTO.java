package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.shared.enums.Visibility;
import dev.srello.cocinillas.user.dto.UserODTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecipeIDTO {
    private String name;
    private String description;
    private List<IngredientIDTO> ingredients;
    private List<InstructionIDTO> instructions;
    private Visibility visibility;
    private List<Long> tagIds;
    private List<RecipeImageIDTO> images;
    private Long totalDuration;
    private UserODTO author;
}
