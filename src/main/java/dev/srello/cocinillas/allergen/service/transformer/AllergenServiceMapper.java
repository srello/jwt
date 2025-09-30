package dev.srello.cocinillas.allergen.service.transformer;

import dev.srello.cocinillas.allergen.dto.AllergenODTO;
import dev.srello.cocinillas.allergen.model.Allergen;
import org.mapstruct.Mapper;

@Mapper
public interface AllergenServiceMapper {
    AllergenODTO toAllergenODTO(Allergen allergen);
}
