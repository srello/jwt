package dev.srello.cocinillas.menu.dto;

import dev.srello.cocinillas.shared.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetMenusIDTO {
    private String name;
    private List<String> tags;
    private Long userId;
    private Visibility visibility;
}
