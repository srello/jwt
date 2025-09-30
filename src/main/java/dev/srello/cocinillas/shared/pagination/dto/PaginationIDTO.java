package dev.srello.cocinillas.shared.pagination.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.springframework.data.domain.Sort.unsorted;

@AllArgsConstructor
@Getter
public class PaginationIDTO {
    private Integer pageNumber;
    private Integer pageSize;
    private List<SortIDTO> sortList;

    public Pageable getPageRequest() {
        Sort sort = sortList.stream()
                .map(SortIDTO::getSort)
                .reduce(unsorted(), Sort::and);
        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
