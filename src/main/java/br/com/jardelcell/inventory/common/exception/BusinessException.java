package br.com.jardelcell.inventory.common.exception;

public abstract class BusinessException extends RuntimeException {
    protected BusinessException(String message) {
        super(message);
    }
}
