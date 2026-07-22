package br.com.jardelcell.inventory.product.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExchangeProductRequest(
        @NotNull(message = "Difference amount is required.")
        BigDecimal differenceAmount,

        String observation
) {
}
