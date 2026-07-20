package br.com.jardelcell.inventory.product.dto;

import java.math.BigDecimal;

public record ExchangeProductRequest(
        BigDecimal differenceAmount,
        String observation
) {
}
