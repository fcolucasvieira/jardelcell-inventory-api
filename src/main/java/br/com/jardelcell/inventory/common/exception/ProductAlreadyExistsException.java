package br.com.jardelcell.inventory.common.exception;

import org.springframework.http.HttpStatus;

public class ProductAlreadyExistsException extends BusinessException {
    public ProductAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
