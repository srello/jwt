package dev.srello.cocinillas.shared.pagination.transformer;

import dev.srello.cocinillas.shared.pagination.dto.PaginationIDTO;
import dev.srello.cocinillas.shared.pagination.rdto.PaginationRQRDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaginationTransformerImpl implements PaginationTransformer {
    private final PaginationMapper mapper;

    @Override
    public PaginationIDTO toPaginationIDTO(PaginationRQRDTO paginationRQRDTO) {
        return mapper.toPaginationIDTO(paginationRQRDTO);
    }
}
