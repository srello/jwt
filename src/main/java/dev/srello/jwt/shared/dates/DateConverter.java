package dev.srello.jwt.shared.dates;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;


public class DateConverter {
    private DateConverter() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }
    public static LocalDateTime dateToLocalDateTime(Date dateToConvert) {
        return Optional.ofNullable(dateToConvert)
                .map(d -> d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .orElse(null);
    }

}
