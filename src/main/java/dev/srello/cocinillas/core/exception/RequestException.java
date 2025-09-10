package dev.srello.cocinillas.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestException extends RuntimeException {

    private HttpStatus httpStatus;
    private Object data;
    private String location;

    public RequestException(String message) {
        super(message);
    }

    public RequestException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public RequestException(HttpStatus httpStatus, String message, String location) {
        super(message);
        this.httpStatus = httpStatus;
        this.location = location;
    }

    public RequestException(HttpStatus httpStatus, String message, Object data) {
        super(message);
        this.httpStatus = httpStatus;
        this.data = data;
    }

    public RequestException(HttpStatus httpStatus, String message, Object data, String location) {
        super(message);
        this.httpStatus = httpStatus;
        this.data = data;
        this.location = location;
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }


}
