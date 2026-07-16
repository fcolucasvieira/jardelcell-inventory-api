package br.com.jardelcell.inventory.product.dto;

import java.math.BigDecimal;

public record SellProductRequest(
        BigDecimal salePrice,
        String observation
) {
}
