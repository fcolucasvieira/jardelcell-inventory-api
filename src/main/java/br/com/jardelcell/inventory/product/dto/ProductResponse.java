package br.com.jardelcell.inventory.product.dto;

import br.com.jardelcell.inventory.product.ProductStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String serialNumber,
        String brand,
        String model,
        ProductStatus status,
        BigDecimal purchasePrice,
        BigDecimal salePrice,
        OffsetDateTime createdAt
) {
}
