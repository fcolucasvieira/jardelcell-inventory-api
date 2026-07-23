package br.com.jardelcell.inventory.inventory.usecase;

import br.com.jardelcell.inventory.common.exception.ResourceNotFoundException;
import br.com.jardelcell.inventory.inventory.InventoryMovement;
import br.com.jardelcell.inventory.inventory.InventoryMovementMapper;
import br.com.jardelcell.inventory.inventory.InventoryMovementRepository;
import br.com.jardelcell.inventory.inventory.MovementType;
import br.com.jardelcell.inventory.inventory.dto.InventoryMovementResponse;
import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListProductMovementsUseCaseTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private InventoryMovementRepository inventoryMovementRepository;
    @Mock
    private InventoryMovementMapper inventoryMovementMapper;

    @InjectMocks
    private ListProductMovementsUseCase listProductMovementsUseCase;

    private Product createProduct() {
        return new Product(
                "SN123456789",
                "356789123456789",
                "Apple",
                "iPhone 15",
                "128GB",
                "Black",
                new BigDecimal("4000.00"),
                new BigDecimal("5500.00"),
                ProductStatus.IN_STOCK
        );
    }

    @Test
    void shouldListProductMovementsSuccessfully() {
        UUID productId = UUID.randomUUID();

        Product product = createProduct();

        InventoryMovement movement = mock(InventoryMovement.class);

        InventoryMovementResponse response = new InventoryMovementResponse(
                MovementType.ENTRY,
                new BigDecimal("4000.00"),
                "Product registered",
                OffsetDateTime.now(),
                "Lucas Vieira"
        );

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        when(inventoryMovementRepository.findByProductId(productId))
                .thenReturn(List.of(movement));
        when(inventoryMovementMapper.toResponse(movement))
                .thenReturn(response);

        List<InventoryMovementResponse> result =
                listProductMovementsUseCase.execute(productId);

        assertEquals(1, result.size());
        assertEquals(response, result.getFirst());

        verify(productRepository).findById(productId);
        verify(inventoryMovementRepository).findByProductId(productId);
        verify(inventoryMovementMapper).toResponse(movement);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        UUID productId = UUID.randomUUID();

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> listProductMovementsUseCase.execute(productId)
        );

        verify(productRepository).findById(productId);
        verify(inventoryMovementRepository, never()).findByProductId(any());
        verify(inventoryMovementMapper, never()).toResponse(any());
    }
}