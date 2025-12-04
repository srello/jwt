package dev.srello.cocinillas.product.service;

import dev.srello.cocinillas.product.dto.GetProductsIDTO;
import dev.srello.cocinillas.product.dto.ProductODTO;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductODTO> getProductsPaginated(GetProductsIDTO getProductsIDTO, PaginationIDTO paginationIDTO);

    Page<ProductODTO> getUserProductsPaginated(GetProductsIDTO getProductsIDTO, PaginationIDTO paginationIDTO);

    List<Product> getProductsByIds(List<Long> productIds, Long userId);
}
