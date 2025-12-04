package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.shared.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetRecipesIDTO {

    private List<String> tags;
    private List<String> ingredients;
    private Visibility visibility;
    private String name;
    private Long userId;
}
