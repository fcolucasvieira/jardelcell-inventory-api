package br.com.jardelcell.inventory.common.exception;

import br.com.jardelcell.inventory.common.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, request);
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(
            BusinessException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = ex.getStatus();

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        ex.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
