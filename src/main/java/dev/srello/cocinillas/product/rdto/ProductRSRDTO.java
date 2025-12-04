package dev.srello.cocinillas.product.rdto;

import dev.srello.cocinillas.allergen.rdto.AllergenRSRDTO;
import dev.srello.cocinillas.product.enums.ProductCategory;
import dev.srello.cocinillas.shared.enums.Visibility;
import dev.srello.cocinillas.tags.rdto.TagRSRDTO;
import dev.srello.cocinillas.unit.enums.Unit;
import dev.srello.cocinillas.unit.rdto.UnitConversionRSRDTO;
import dev.srello.cocinillas.user.rdto.AuthorRSRDTO;

import java.util.List;

public record ProductRSRDTO(
        Long id,
        String name,
        ProductCategory productCategory,
        Unit baseUnit,
        List<UnitConversionRSRDTO> unitConversions,
        Double calories,
        Double protein,
        Double carbohydrates,
        Double fat,
        List<AllergenRSRDTO> allergens,
        List<TagRSRDTO> tags,
        Visibility visibility,
        AuthorRSRDTO author
) {
}
