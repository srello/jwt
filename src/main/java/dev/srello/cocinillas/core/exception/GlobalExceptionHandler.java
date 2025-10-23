package dev.srello.cocinillas.core.exception;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.core.exception.rdto.ApiErrorRSRDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RequestException.class)
    public final ResponseEntity<ApiErrorRSRDTO> handleRequestExceptions(RequestException ex) {

        return ResponseEntity.status(ex.getHttpStatus()).body(
                new ApiErrorRSRDTO(ex.getApiCode(), ex.getMessage())
        );
    }
}
