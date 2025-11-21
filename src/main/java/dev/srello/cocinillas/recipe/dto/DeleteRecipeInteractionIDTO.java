package dev.srello.cocinillas.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteRecipeInteractionIDTO {
    private Long userId;
    private Long id;
}
