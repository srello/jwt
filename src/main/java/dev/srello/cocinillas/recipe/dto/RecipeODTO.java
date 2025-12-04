package dev.srello.cocinillas.recipe.dto;

import dev.srello.cocinillas.shared.enums.Visibility;
import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.user.dto.AuthorODTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URL;
import java.util.List;

@Getter
@AllArgsConstructor
public class RecipeODTO {

    private Long id;
    private String name;
    private String description;
    private List<IngredientODTO> ingredients;
    private List<InstructionODTO> instructions;
    private Visibility visibility;
    private List<TagODTO> tags;
    private List<URL> imageUrls;
    private Integer totalDuration;
    private MacrosODTO macros;
    private Long likes;
    private Boolean isLiked;
    private Boolean isSaved;
    private AuthorODTO author;
}
