package dev.srello.cocinillas.core.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String apiCode;

    public RequestException(HttpStatus httpStatus, String message, String apiCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.apiCode = apiCode;
    }
}
