package dev.srello.cocinillas.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MenuMealIDTO {
    private String name;
    private List<Long> recipeIds;
    private LocalTime hour;
    private Integer dayIndex;
}
