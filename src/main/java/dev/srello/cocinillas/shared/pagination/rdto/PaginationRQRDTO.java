package dev.srello.cocinillas.shared.pagination.rdto;

import java.util.List;

import static java.util.List.of;
import static java.util.Objects.isNull;
import static org.springframework.data.domain.Sort.Direction.ASC;

public record PaginationRQRDTO(
        Integer pageNumber,
        Integer pageSize,
        List<SortRQRDTO> sortList
) {
    public static PaginationRQRDTO defaultPagination = new PaginationRQRDTO(0, 16, of(new SortRQRDTO(ASC, "id")));

    public PaginationRQRDTO {
        pageNumber = isNull(pageNumber) ? defaultPagination.pageNumber : pageNumber;
        pageSize = isNull(pageSize) ? defaultPagination.pageSize : pageSize;
        sortList = isNull(sortList) ? defaultPagination.sortList : sortList;
    }
}
