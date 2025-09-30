package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipeIDTO {
    private PaginationIDTO pagination;

}
