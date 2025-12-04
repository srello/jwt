package dev.srello.cocinillas.product.service.transformer;

import dev.srello.cocinillas.allergen.service.transformer.AllergenServiceMapper;
import dev.srello.cocinillas.product.dto.ProductODTO;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.tags.service.transformer.TagServiceMapper;
import dev.srello.cocinillas.user.service.transformer.UserServiceMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(uses = {AllergenServiceMapper.class, TagServiceMapper.class, UserServiceMapper.class})
public interface ProductServiceMapper {
    ProductODTO toProductODTO(Product product, @Context Long userId);
}
