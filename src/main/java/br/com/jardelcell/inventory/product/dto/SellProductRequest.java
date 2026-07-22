package br.com.jardelcell.inventory.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SellProductRequest(
        @NotNull(message = "Sale price is required.")
        @Positive(message = "Sale price must be greater than zero.")
        BigDecimal salePrice,

        String observation
) {
}
