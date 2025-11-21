package dev.srello.cocinillas.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InstructionIDTO {
    private String description;
    private Double timer;
    private Boolean hasImage;
}
