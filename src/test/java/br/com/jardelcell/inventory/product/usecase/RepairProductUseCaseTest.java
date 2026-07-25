package br.com.jardelcell.inventory.product.usecase;

import br.com.jardelcell.inventory.common.exception.ResourceNotFoundException;
import br.com.jardelcell.inventory.inventory.InventoryMovementService;
import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductMapper;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import br.com.jardelcell.inventory.product.dto.RepairProductRequest;
import br.com.jardelcell.inventory.support.fixture.ProductFixture;
import br.com.jardelcell.inventory.support.fixture.ProductResponseFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepairProductUseCaseTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private InventoryMovementService inventoryMovementService;

    @InjectMocks
    private RepairProductUseCase repairProductUseCase;

    @Test
    void shouldRepairProductSuccessfully() {
        UUID productId = UUID.randomUUID();

        RepairProductRequest request = new RepairProductRequest(
                new BigDecimal("200.00"),
                "R$200 repair of the main screen"
        );

        Product product = ProductFixture.defective();

        ProductResponse response = ProductResponseFixture.inStock();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toResponse(product)).thenReturn(response);

        ProductResponse result = repairProductUseCase.execute(productId, request);

        assertEquals(response, result);
        assertEquals(
                ProductStatus.IN_STOCK,
                result.status()
        );

        verify(productRepository).findById(productId);
        verify(inventoryMovementService).registerRepair(
                product,
                request.repairPrice(),
                request.observation()
        );
        verify(productMapper).toResponse(product);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        UUID productId = UUID.randomUUID();

        RepairProductRequest request = new RepairProductRequest(
                new BigDecimal("200.00"),
                "R$200 repair of the main screen"
        );

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> repairProductUseCase.execute(productId, request)
        );

        verify(productRepository).findById(productId);

        verify(inventoryMovementService, never()).registerRepair(any(), any(), any());
        verify(productMapper, never()).toResponse(any());
    }
}