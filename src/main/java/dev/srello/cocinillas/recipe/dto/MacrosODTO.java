package dev.srello.cocinillas.recipe.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MacrosODTO {
    private Double calories;
    private Double fat;
    private Double protein;
    private Double carbohydrates;
}
