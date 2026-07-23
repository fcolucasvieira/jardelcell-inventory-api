package br.com.jardelcell.inventory.product.usecase;

import br.com.jardelcell.inventory.common.exception.ResourceNotFoundException;
import br.com.jardelcell.inventory.inventory.InventoryMovementService;
import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductMapper;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import br.com.jardelcell.inventory.product.dto.UnreserveProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class UnreserveProductUseCaseTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private InventoryMovementService inventoryMovementService;

    @InjectMocks
    private UnreserveProductUseCase unreserveProductUseCase;

    private UnreserveProductRequest createRequest() {
        return new UnreserveProductRequest(
                "The client cancelled the reservation scheduled for tomorrow, 24/07"
        );
    }

    @Test
    void shouldUnreservedProductSuccessfully() {
        UUID productId = UUID.randomUUID();

        UnreserveProductRequest request = createRequest();

        Product product = new Product(
                "SN123456789",
                "356789123456789",
                "Apple",
                "iPhone 15",
                "128GB",
                "Black",
                new BigDecimal("4000.00"),
                new BigDecimal("5500.00"),
                ProductStatus.RESERVED
        );

        ProductResponse response = new ProductResponse(
                UUID.randomUUID(),
                "SN123456789",
                "Apple",
                "iPhone 15",
                ProductStatus.IN_STOCK,
                new BigDecimal("4000.00"),
                new BigDecimal("5500.00"),
                OffsetDateTime.now()
        );

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        when(productMapper.toResponse(product))
                .thenReturn(response);

        ProductResponse result = unreserveProductUseCase.execute(productId, request);

        assertEquals(response, result);
        assertEquals(
                ProductStatus.IN_STOCK,
                product.getStatus()
        );

        verify(productRepository).findById(productId);
        verify(inventoryMovementService).registerUnreserve(
                product,
                request.observation()
        );
        verify(productMapper).toResponse(product);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        UUID productId = UUID.randomUUID();

        UnreserveProductRequest request = createRequest();

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> unreserveProductUseCase.execute(productId, request)
        );

        verify(productRepository).findById(productId);

        verify(inventoryMovementService, never()).registerUnreserve(any(), any());
        verify(productMapper, never()).toResponse(any());
    }
}