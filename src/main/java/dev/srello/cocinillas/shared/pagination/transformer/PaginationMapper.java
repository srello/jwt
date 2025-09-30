package dev.srello.cocinillas.shared.pagination.transformer;

import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.shared.pagination.rdto.PaginationRQRDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PaginationMapper {
    PaginationIDTO toPaginationIDTO(PaginationRQRDTO paginationRQRDTO);
}
