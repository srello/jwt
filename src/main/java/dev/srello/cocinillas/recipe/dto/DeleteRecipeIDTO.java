package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.user.dto.UserODTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteRecipeIDTO {
    private Long recipeId;
    private UserODTO user;
}
