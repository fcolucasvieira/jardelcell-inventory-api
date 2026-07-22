package br.com.jardelcell.inventory.common.dto;

public record FieldValidationError(
        String field,
        String message
) {
}
