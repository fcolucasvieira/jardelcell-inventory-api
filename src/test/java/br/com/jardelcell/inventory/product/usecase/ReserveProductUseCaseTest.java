package br.com.jardelcell.inventory.product.usecase;

import br.com.jardelcell.inventory.common.exception.ResourceNotFoundException;
import br.com.jardelcell.inventory.support.fixture.ProductFixture;
import br.com.jardelcell.inventory.support.fixture.ProductResponseFixture;
import br.com.jardelcell.inventory.inventory.InventoryMovementService;
import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductMapper;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import br.com.jardelcell.inventory.product.dto.ReserveProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ReserveProductUseCaseTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private InventoryMovementService inventoryMovementService;

    @InjectMocks
    private ReserveProductUseCase reserveProductUseCase;

    private ReserveProductRequest createRequest() {
        return new ReserveProductRequest(
                "Product reserved for tomorrow, 24/07"
        );
    }

    @Test
    void shouldReservedProductSuccessfully() {
        UUID productId = UUID.randomUUID();

        ReserveProductRequest request = createRequest();

        Product product = ProductFixture.inStock();

        ProductResponse response = ProductResponseFixture.reserved();

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        when(productMapper.toResponse(product))
                .thenReturn(response);

        ProductResponse result = reserveProductUseCase.execute(productId, request);

        assertEquals(response, result);
        assertEquals(
                ProductStatus.RESERVED,
                product.getStatus()
        );

        verify(productRepository).findById(productId);
        verify(inventoryMovementService).registerReserve(
                product,
                request.observation()
        );
        verify(productMapper).toResponse(product);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        UUID productId = UUID.randomUUID();

        ReserveProductRequest request = createRequest();

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> reserveProductUseCase.execute(productId, request)
        );

        verify(productRepository).findById(productId);

        verify(inventoryMovementService, never()).registerReserve(any(), any());
        verify(productMapper, never()).toResponse(any());
    }
}