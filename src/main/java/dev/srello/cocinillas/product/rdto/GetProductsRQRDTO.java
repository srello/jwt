package dev.srello.cocinillas.product.rdto;

import dev.srello.cocinillas.product.enums.ProductCategory;
import dev.srello.cocinillas.product.enums.ProductCategory.ProductGroup;
import dev.srello.cocinillas.shared.enums.Visibility;

import java.util.List;

public record GetProductsRQRDTO(
        String name,
        List<String> allergens,
        List<String> tags,
        List<ProductGroup> groups,
        List<ProductCategory> categories,
        Visibility visibility
) {
}
