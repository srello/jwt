package dev.srello.cocinillas.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipeAuthorODTO {
    private String username;
    private Boolean isUserAuthor;
}
