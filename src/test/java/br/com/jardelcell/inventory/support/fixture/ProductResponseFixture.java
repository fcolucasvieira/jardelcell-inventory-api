package br.com.jardelcell.inventory.support.fixture;

import br.com.jardelcell.inventory.product.ProductStatus;
import br.com.jardelcell.inventory.product.dto.ProductResponse;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public final class ProductResponseFixture {

    private ProductResponseFixture() {
    }

    public static ProductResponse inStock() {
        return withStatus(ProductStatus.IN_STOCK);
    }

    public static ProductResponse reserved() {
        return withStatus(ProductStatus.RESERVED);
    }

    public static ProductResponse sold() {
        return withStatus(ProductStatus.SOLD);
    }

    public static ProductResponse exchanged() {
        return withStatus(ProductStatus.EXCHANGED);
    }

    public static ProductResponse defective() {
        return withStatus(ProductStatus.DEFECTIVE);
    }

    public static ProductResponse withStatus(ProductStatus status) {
        return new ProductResponse(
                UUID.randomUUID(),
                "SN123456789",
                "Apple",
                "iPhone 15",
                status,
                new BigDecimal("4000.00"),
                new BigDecimal("5500.00"),
                OffsetDateTime.now()
        );
    }
}