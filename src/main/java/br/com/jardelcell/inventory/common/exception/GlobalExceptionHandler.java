package br.com.jardelcell.inventory.common.exception;

import br.com.jardelcell.inventory.common.dto.ApiErrorResponse;
import br.com.jardelcell.inventory.common.dto.FieldValidationError;
import br.com.jardelcell.inventory.common.dto.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldValidationError> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new FieldValidationError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                        )
                )
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

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
