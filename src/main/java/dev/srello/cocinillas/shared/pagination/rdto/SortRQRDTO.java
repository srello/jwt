package dev.srello.cocinillas.shared.pagination.rdto;

import org.springframework.data.domain.Sort.Direction;

public record SortRQRDTO(
        Direction direction,
        String sortField
) {

}
