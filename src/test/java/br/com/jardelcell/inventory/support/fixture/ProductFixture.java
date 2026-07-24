package br.com.jardelcell.inventory.support.fixture;

import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductStatus;

import java.math.BigDecimal;

public final class ProductFixture {

    private ProductFixture() {}

    public static Product inStock() {
        return withStatus(ProductStatus.IN_STOCK);
    }

    public static Product reserved() {
        return withStatus(ProductStatus.RESERVED);
    }

    public static Product sold() {
        return withStatus(ProductStatus.SOLD);
    }

    public static Product exchanged() {
        return withStatus(ProductStatus.EXCHANGED);
    }

    public static Product defective() {
        return withStatus(ProductStatus.DEFECTIVE);
    }

    public static Product withStatus(ProductStatus status) {
        return new Product(
                "SN123456789",
                "356789123456789",
                "Apple",
                "iPhone 15",
                "128GB",
                "Black",
                new BigDecimal("4000.00"),
                new BigDecimal("5500.00"),
                status
        );
    }
}
