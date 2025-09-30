package dev.srello.cocinillas.unit.dto;

import dev.srello.cocinillas.unit.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnitConversionODTO {

    private Long id;
    private Unit targetUnit;
    private Double conversionFactor;
}
