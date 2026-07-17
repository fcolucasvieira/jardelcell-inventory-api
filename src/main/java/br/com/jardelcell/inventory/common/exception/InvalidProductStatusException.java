package br.com.jardelcell.inventory.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidProductStatusException extends BusinessException {
    public InvalidProductStatusException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
