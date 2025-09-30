package dev.srello.cocinillas.unit.rdto;

import dev.srello.cocinillas.unit.enums.Unit;

public record UnitConversionRSRDTO(
        Long id,
        Unit targetUnit,
        Double conversionFactor
) {
}
