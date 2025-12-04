package dev.srello.cocinillas.product.controller.transformer;

import dev.srello.cocinillas.product.dto.GetProductsIDTO;
import dev.srello.cocinillas.product.dto.ProductODTO;
import dev.srello.cocinillas.product.rdto.GetProductsRQRDTO;
import dev.srello.cocinillas.product.rdto.ProductRSRDTO;
import lombok.NonNull;
import org.springframework.data.domain.Page;

public interface ProductControllerTransformer {
    GetProductsIDTO toGetProductsIDTO(@NonNull GetProductsRQRDTO getProductsRQRDTO, @NonNull Long userId);

    Page<ProductRSRDTO> toProductsRSRDTO(@NonNull Page<ProductODTO> productODTOS);
}
