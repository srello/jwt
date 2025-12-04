package dev.srello.cocinillas.menu.dto;

import dev.srello.cocinillas.shared.enums.Visibility;
import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.user.dto.AuthorODTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuODTO {
    private Long id;
    private String name;
    private String description;
    private List<MenuMealODTO> menuMeals;
    private AuthorODTO author;
    private List<TagODTO> tags;
    private Long likes;
    private Visibility visibility;
    private Boolean isLiked;
    private Boolean isSaved;
}
