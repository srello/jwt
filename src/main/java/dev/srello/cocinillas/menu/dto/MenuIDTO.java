package dev.srello.cocinillas.menu.dto;

import dev.srello.cocinillas.shared.enums.Visibility;
import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuIDTO {
    private String name;
    private String description;
    private List<MenuMealIDTO> menuMeals;
    private User author;
    private List<TagODTO> tags;
    private Visibility visibility;
}
