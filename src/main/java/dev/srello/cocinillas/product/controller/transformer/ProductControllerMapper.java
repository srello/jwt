package dev.srello.cocinillas.product.controller.transformer;

import dev.srello.cocinillas.allergen.controller.transformer.AllergenControllerMapper;
import dev.srello.cocinillas.product.dto.ProductODTO;
import dev.srello.cocinillas.product.rdto.ProductRSRDTO;
import dev.srello.cocinillas.tags.controller.transformer.TagControllerMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {TagControllerMapper.class, AllergenControllerMapper.class})
public interface ProductControllerMapper {
    ProductRSRDTO toProductRSRDTO(ProductODTO productODTO);
}
