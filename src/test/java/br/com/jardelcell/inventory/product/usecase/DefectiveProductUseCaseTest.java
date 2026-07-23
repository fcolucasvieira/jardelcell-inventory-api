package br.com.jardelcell.inventory.product.usecase;

import br.com.jardelcell.inventory.common.exception.ResourceNotFoundException;
import br.com.jardelcell.inventory.inventory.InventoryMovementService;
import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductMapper;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import br.com.jardelcell.inventory.product.dto.DefectiveProductRequest;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
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
class DefectiveProductUseCaseTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private InventoryMovementService inventoryMovementService;

    @InjectMocks
    private DefectiveProductUseCase defectiveProductUseCase;

    private DefectiveProductRequest createRequest() {
        return new DefectiveProductRequest(
                "Product with a defective side button (Power On/Off)"
        );
    }

    @ParameterizedTest
    @EnumSource(
            value = ProductStatus.class,
            names = {
                    "IN_STOCK", "RESERVED"
            }
    )
    void shouldMarkAsDefectiveProductSuccessfully(ProductStatus status) {
        UUID productId = UUID.randomUUID();

        DefectiveProductRequest request = createRequest();

        Product product = new Product(
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

        ProductResponse response = new ProductResponse(
                UUID.randomUUID(),
                "SN123456789",
                "Apple",
                "iPhone 15",
                ProductStatus.DEFECTIVE,
                new BigDecimal("4000.00"),
                new BigDecimal("5500.00"),
                OffsetDateTime.now()
        );

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        when(productMapper.toResponse(product))
                .thenReturn(response);

        ProductResponse result = defectiveProductUseCase.execute(productId, request);

        assertEquals(response, result);
        assertEquals(
                ProductStatus.DEFECTIVE,
                product.getStatus()
        );

        verify(productRepository).findById(productId);
        verify(inventoryMovementService).registerDefective(
                product,
                request.observation()
        );
        verify(productMapper).toResponse(product);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        UUID productId = UUID.randomUUID();

        DefectiveProductRequest request = createRequest();

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> defectiveProductUseCase.execute(productId, request)
        );

        verify(productRepository).findById(productId);

        verify(inventoryMovementService, never()).registerDefective(any(), any());
        verify(productMapper, never()).toResponse(any());
    }
}