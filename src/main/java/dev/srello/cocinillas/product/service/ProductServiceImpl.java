package dev.srello.cocinillas.product.service;

import dev.srello.cocinillas.product.dto.GetProductsIDTO;
import dev.srello.cocinillas.product.dto.ProductODTO;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.product.reposiroty.ProductRepository;
import dev.srello.cocinillas.product.service.transformer.ProductServiceTransformer;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductSpecificationService productSpecificationService;
    private final ProductServiceTransformer transformer;

    @Override
    public Page<ProductODTO> getProductsPaginated(GetProductsIDTO getProductsIDTO, PaginationIDTO paginationIDTO) {
        Specification<Product> productsSpecification = productSpecificationService.buildGetProductsPaginatedSpecification(getProductsIDTO);
        Page<Product> products = repository.findAll(productsSpecification, paginationIDTO.getPageRequest());
        return transformer.toProductODTO(products, getProductsIDTO.getUserId());
    }

    @Override
    public Page<ProductODTO> getUserProductsPaginated(GetProductsIDTO getProductsIDTO, PaginationIDTO paginationIDTO) {
        Specification<Product> productsSpecification = productSpecificationService.buildGetUserProductsPaginatedSpecification(getProductsIDTO);
        Page<Product> products = repository.findAll(productsSpecification, paginationIDTO.getPageRequest());
        return transformer.toProductODTO(products, getProductsIDTO.getUserId());
    }

    @Override
    public List<Product> getProductsByIds(List<Long> productIds, Long userId) {
        return repository.findAllById(productIds);
    }
}
