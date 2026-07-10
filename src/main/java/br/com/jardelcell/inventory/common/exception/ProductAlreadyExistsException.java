package br.com.jardelcell.inventory.common.exception;

public class ProductAlreadyExistsException extends BusinessException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
