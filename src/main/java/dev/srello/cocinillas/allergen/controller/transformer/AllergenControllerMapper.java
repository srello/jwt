package dev.srello.cocinillas.allergen.controller.transformer;

import dev.srello.cocinillas.allergen.dto.AllergenODTO;
import dev.srello.cocinillas.allergen.rdto.AllergenRSRDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AllergenControllerMapper {
    AllergenRSRDTO toAllergenRSRDTO(AllergenODTO allergenODTO);
}
