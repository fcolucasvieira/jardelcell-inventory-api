package br.com.jardelcell.inventory.product;

import br.com.jardelcell.inventory.common.exception.InvalidProductStatusException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product createProduct(ProductStatus status) {
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

    @Test
    void shouldMarkProductAsInStock() {
        Product product = createProduct(ProductStatus.RESERVED);

        product.markAsInStock();

        assertEquals(ProductStatus.IN_STOCK, product.getStatus());
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "IN_STOCK", "SOLD", "EXCHANGED", "DEFECTIVE"
    }
    )
    void shouldThrowExceptionWhenMarkingProductAsInStockFromUnavailableStatus(ProductStatus status) {
        Product product = createProduct(status);

        assertThrows(
                InvalidProductStatusException.class,
                product::markAsInStock
        );
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "IN_STOCK", "RESERVED"
            }
    )
    void shouldMarkProductAsSold(ProductStatus status) {
        Product product = createProduct(status);

        product.markAsSold();

        assertEquals(ProductStatus.SOLD, product.getStatus());
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "SOLD", "EXCHANGED", "DEFECTIVE"
            }
    )
    void shouldThrowExceptionWhenSellingUnavailableProduct(ProductStatus status) {
        Product product = createProduct(status);

        assertThrows(
                InvalidProductStatusException.class,
                product::markAsSold
        );
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "IN_STOCK", "RESERVED"
            }
    )
    void shouldMarkProductAsExchanged(ProductStatus status) {
        Product product = createProduct(status);

        product.markAsExchanged();

        assertEquals(ProductStatus.EXCHANGED, product.getStatus());
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "SOLD", "EXCHANGED", "DEFECTIVE"
            }
    )
    void shouldThrowExceptionWhenExchangingUnavailableProduct(ProductStatus status) {
        Product product = createProduct(status);

        assertThrows(
                InvalidProductStatusException.class,
                product::markAsExchanged
        );
    }

    @Test
    void shouldMarkProductAsReserved() {
        Product product = createProduct(ProductStatus.IN_STOCK);

        product.markAsReserved();

        assertEquals(ProductStatus.RESERVED, product.getStatus());
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
            "SOLD", "EXCHANGED", "DEFECTIVE", "RESERVED"
            }
    )
    void shouldThrowExceptionWhenReservingUnavailableProduct(ProductStatus status) {
        Product product = createProduct(status);

        assertThrows(
                InvalidProductStatusException.class,
                product::markAsReserved
        );
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "IN_STOCK", "RESERVED"
            }
    )
    void shouldMarkProductAsDefective(ProductStatus status) {
        Product product = createProduct(status);

        product.markAsDefective();

        assertEquals(ProductStatus.DEFECTIVE, product.getStatus());
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "SOLD", "EXCHANGED", "DEFECTIVE"
            }
    )
    void shouldThrowExceptionWhenMarkingUnavailableProductAsDefective(ProductStatus status) {
        Product product = createProduct(status);

        assertThrows(
                InvalidProductStatusException.class,
                product::markAsDefective
        );
    }
}