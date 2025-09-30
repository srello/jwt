package dev.srello.cocinillas.product.dto;

import dev.srello.cocinillas.allergen.dto.AllergenODTO;
import dev.srello.cocinillas.product.enums.ProductCategory;
import dev.srello.cocinillas.tags.dto.TagODTO;
import dev.srello.cocinillas.unit.dto.UnitConversionODTO;
import dev.srello.cocinillas.unit.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductODTO {
    private Long id;
    private String name;
    private ProductCategory productCategory;
    private Unit baseUnit;
    private List<UnitConversionODTO> unitConversions;
    private Double calories;
    private Double protein;
    private Double carbohydrates;
    private Double fat;
    private List<AllergenODTO> allergens;
    private List<TagODTO> tags;
}
