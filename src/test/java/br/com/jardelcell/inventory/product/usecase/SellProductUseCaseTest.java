package br.com.jardelcell.inventory.product.usecase;

import br.com.jardelcell.inventory.common.exception.ResourceNotFoundException;
import br.com.jardelcell.inventory.fixture.ProductFixture;
import br.com.jardelcell.inventory.fixture.ProductResponseFixture;
import br.com.jardelcell.inventory.inventory.InventoryMovementService;
import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductMapper;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import br.com.jardelcell.inventory.product.dto.SellProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellProductUseCaseTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private InventoryMovementService inventoryMovementService;

    @InjectMocks
    private SellProductUseCase sellProductUseCase;

    private SellProductRequest createRequest() {
        return  new SellProductRequest(
                new BigDecimal("4500.00"),
                "Product sold for R$ 4500"
        );
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "IN_STOCK", "RESERVED"
            }
    )
    void shouldSellProductSuccessfully(ProductStatus status) {
        UUID productId = UUID.randomUUID();

        SellProductRequest request = createRequest();

        Product product = ProductFixture.withStatus(status);

        ProductResponse response = ProductResponseFixture.sold();

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        when(productMapper.toResponse(product))
                .thenReturn(response);

        ProductResponse result = sellProductUseCase.execute(productId, request);

        assertEquals(response, result);
        assertEquals(
                ProductStatus.SOLD,
                product.getStatus()
        );

        verify(productRepository).findById(productId);
        verify(inventoryMovementService).registerSale(
                product,
                request.salePrice(),
                request.observation()
        );
        verify(productMapper).toResponse(product);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        UUID productId = UUID.randomUUID();

        SellProductRequest request = createRequest();

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> sellProductUseCase.execute(productId, request)
        );

        verify(productRepository).findById(productId);

        verify(inventoryMovementService, never()).registerSale(any(), any(), any());
        verify(productMapper, never()).toResponse(any());
    }
}