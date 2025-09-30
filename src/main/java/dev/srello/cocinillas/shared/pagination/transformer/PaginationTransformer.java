package dev.srello.cocinillas.shared.pagination.transformer;

import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.shared.pagination.rdto.PaginationRQRDTO;

public interface PaginationTransformer {
    PaginationIDTO toPaginationIDTO(PaginationRQRDTO paginationRQRDTO);
}
