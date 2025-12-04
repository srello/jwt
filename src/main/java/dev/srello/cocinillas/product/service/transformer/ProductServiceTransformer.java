package dev.srello.cocinillas.product.service.transformer;

import dev.srello.cocinillas.product.dto.ProductODTO;
import dev.srello.cocinillas.product.model.Product;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductServiceTransformer {
    Page<ProductODTO> toProductODTO(@NonNull Page<Product> products, @NonNull Long userId);

    List<ProductODTO> toProductODTO(@NonNull List<Product> products, @NonNull Long userId);
}
