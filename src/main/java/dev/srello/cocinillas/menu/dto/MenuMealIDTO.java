package dev.srello.cocinillas.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class MenuMealIDTO {
    private String name;
    private Long recipeId;
    private LocalTime hour;
    private Integer dayIndex;
}
