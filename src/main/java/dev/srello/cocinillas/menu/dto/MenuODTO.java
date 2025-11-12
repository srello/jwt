package dev.srello.cocinillas.menu.dto;

import dev.srello.cocinillas.menu.enums.MenuVisibility;
import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuODTO {
    private Long id;
    private String name;
    private List<MenuMealODTO> menuMeals;
    private User author;
    private List<TagODTO> tags;
    private Long likes;
    private MenuVisibility visibility;
    private Boolean isLiked;
    private Boolean isSaved;
}
