package br.com.jardelcell.inventory.product;

import br.com.jardelcell.inventory.common.exception.InvalidProductStatusException;
import br.com.jardelcell.inventory.support.fixture.ProductFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void shouldMarkProductAsInStock() {
        Product product = ProductFixture.reserved();

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
        Product product = ProductFixture.withStatus(status);

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
        Product product = ProductFixture.withStatus(status);

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
        Product product = ProductFixture.withStatus(status);

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
        Product product = ProductFixture.withStatus(status);

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
        Product product = ProductFixture.withStatus(status);

        assertThrows(
                InvalidProductStatusException.class,
                product::markAsExchanged
        );
    }

    @Test
    void shouldMarkProductAsReserved() {
        Product product = ProductFixture.inStock();

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
        Product product = ProductFixture.withStatus(status);

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
        Product product = ProductFixture.withStatus(status);

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
        Product product = ProductFixture.withStatus(status);

        assertThrows(
                InvalidProductStatusException.class,
                product::markAsDefective
        );
    }
}