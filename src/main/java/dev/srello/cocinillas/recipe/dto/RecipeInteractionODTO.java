package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.shared.enums.InteractionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipeInteractionODTO {
    private Long id;
    private Long userId;
    private Long recipeId;
    private InteractionType type;
}
