package dev.srello.cocinillas.product.dto;

import dev.srello.cocinillas.product.enums.ProductCategory;
import dev.srello.cocinillas.product.enums.ProductCategory.ProductGroup;
import dev.srello.cocinillas.shared.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetProductsIDTO {
    private String name;
    private List<String> allergens;
    private List<String> tags;
    private List<ProductGroup> groups;
    private List<ProductCategory> categories;
    private Visibility visibility;
    private Long userId;
}
