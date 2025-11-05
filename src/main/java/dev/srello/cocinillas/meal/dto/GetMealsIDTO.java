package dev.srello.cocinillas.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetMealsIDTO {
    private Long userId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
