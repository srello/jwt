package dev.srello.cocinillas.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InstructionODTO {
    private Long id;
    private String description;
    private Double timer;
    private String imageUrl;
}
