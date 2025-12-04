package dev.srello.cocinillas.product.controller;


import dev.srello.cocinillas.product.controller.transformer.ProductControllerTransformer;
import dev.srello.cocinillas.product.dto.GetProductsIDTO;
import dev.srello.cocinillas.product.dto.ProductODTO;
import dev.srello.cocinillas.product.rdto.GetProductsRQRDTO;
import dev.srello.cocinillas.product.rdto.ProductRSRDTO;
import dev.srello.cocinillas.product.service.ProductService;
import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.shared.pagination.rdto.PaginationRQRDTO;
import dev.srello.cocinillas.shared.pagination.transformer.PaginationTransformer;
import dev.srello.cocinillas.user.CurrentUser;
import dev.srello.cocinillas.user.dto.UserODTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static dev.srello.cocinillas.core.request.RequestConstants.ME_PATH_VARIABLE;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final PaginationTransformer paginationTransformer;
    private final ProductControllerTransformer transformer;
    private final ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductRSRDTO>> getProductsPaginated(@Valid GetProductsRQRDTO getProductsRQRDTO, @Valid PaginationRQRDTO paginationRQRDTO, @CurrentUser UserODTO currentUser) {
        GetProductsIDTO getProductsIDTO = transformer.toGetProductsIDTO(getProductsRQRDTO, currentUser.getId());
        PaginationIDTO paginationIDTO = paginationTransformer.toPaginationIDTO(paginationRQRDTO);
        Page<ProductODTO> productODTOS = service.getProductsPaginated(getProductsIDTO, paginationIDTO);
        Page<ProductRSRDTO> productRSRDTOS = transformer.toProductsRSRDTO(productODTOS);
        return ok(productRSRDTOS);
    }

    @GetMapping(ME_PATH_VARIABLE)
    public ResponseEntity<Page<ProductRSRDTO>> getUserProductsPaginated(@Valid GetProductsRQRDTO getProductsRQRDTO, @Valid PaginationRQRDTO paginationRQRDTO, @CurrentUser UserODTO currentUser) {
        GetProductsIDTO getProductsIDTO = transformer.toGetProductsIDTO(getProductsRQRDTO, currentUser.getId());
        PaginationIDTO paginationIDTO = paginationTransformer.toPaginationIDTO(paginationRQRDTO);
        Page<ProductODTO> productODTOS = service.getUserProductsPaginated(getProductsIDTO, paginationIDTO);
        Page<ProductRSRDTO> productRSRDTOS = transformer.toProductsRSRDTO(productODTOS);
        return ok(productRSRDTOS);
    }
}
