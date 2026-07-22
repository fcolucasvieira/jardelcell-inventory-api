package br.com.jardelcell.inventory.common.dto;

import java.time.Instant;
import java.util.List;

public record ValidationErrorResponse(
        Instant timestamp,
        Integer status,
        List<FieldValidationError> errors
) {
}
