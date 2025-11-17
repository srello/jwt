package dev.srello.cocinillas.meal.rdto;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;


public record DeleteMealsRQRDTO(
        @NotNull(value = "Start date time is required")
        LocalDateTime startDateTime,
        @NotNull(value = "End date time is required")
        LocalDateTime endDateTime
) {

}
