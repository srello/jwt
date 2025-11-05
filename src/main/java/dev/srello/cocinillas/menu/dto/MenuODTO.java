package dev.srello.cocinillas.menu.dto;

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
}
