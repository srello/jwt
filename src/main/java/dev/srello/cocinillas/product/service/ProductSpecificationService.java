package dev.srello.cocinillas.product.service;

import dev.srello.cocinillas.product.dto.GetProductsIDTO;
import dev.srello.cocinillas.product.model.Product;
import org.springframework.data.jpa.domain.Specification;

public interface ProductSpecificationService {
    Specification<Product> buildGetProductsPaginatedSpecification(GetProductsIDTO getProductsIDTO);

    Specification<Product> buildGetUserProductsPaginatedSpecification(GetProductsIDTO getProductsIDTO);
}
