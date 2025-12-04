package dev.srello.cocinillas.product.service.transformer;

import dev.srello.cocinillas.product.dto.ProductODTO;
import dev.srello.cocinillas.product.model.Product;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductServiceTransformerImpl implements ProductServiceTransformer {
    private final ProductServiceMapper mapper;

    @Override
    public Page<ProductODTO> toProductODTO(@NotNull Page<Product> products, @NotNull Long userId) {
        return products.map(product -> mapper.toProductODTO(product, userId));
    }

    @Override
    public List<ProductODTO> toProductODTO(@NonNull List<Product> products, @NonNull Long userId) {
        return products.stream()
                .map(product -> mapper.toProductODTO(product, userId))
                .toList();
    }
}
