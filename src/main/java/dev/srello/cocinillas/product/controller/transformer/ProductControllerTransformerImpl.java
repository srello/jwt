package dev.srello.cocinillas.product.controller.transformer;

import dev.srello.cocinillas.product.dto.GetProductsIDTO;
import dev.srello.cocinillas.product.dto.ProductODTO;
import dev.srello.cocinillas.product.rdto.GetProductsRQRDTO;
import dev.srello.cocinillas.product.rdto.ProductRSRDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductControllerTransformerImpl implements ProductControllerTransformer {
    private final ProductControllerMapper mapper;

    @Override
    public GetProductsIDTO toGetProductsIDTO(@NotNull GetProductsRQRDTO getProductsRQRDTO, @NonNull Long userId) {
        return mapper.toGetProductsIDTO(getProductsRQRDTO, userId);
    }

    @Override
    public Page<ProductRSRDTO> toProductsRSRDTO(@NotNull Page<ProductODTO> productODTOS) {
        return productODTOS.map(mapper::toProductRSRDTO);
    }
}
