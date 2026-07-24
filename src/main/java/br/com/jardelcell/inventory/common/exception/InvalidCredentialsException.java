package br.com.jardelcell.inventory.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BusinessException {
    public InvalidCredentialsException() {
      super(
              "Invalid email or password.",
              HttpStatus.UNAUTHORIZED
      );
    }
}
