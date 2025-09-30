package dev.srello.cocinillas.shared.pagination.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import static org.springframework.data.domain.Sort.by;

@AllArgsConstructor
@Getter
public class SortIDTO {
    private Direction direction;
    private String sortField;

    public Sort getSort() {
        return by(direction, sortField);
    }
}
