package br.com.jardelcell.inventory.product.usecase;

import br.com.jardelcell.inventory.common.exception.ProductAlreadyExistsException;
import br.com.jardelcell.inventory.fixture.ProductFixture;
import br.com.jardelcell.inventory.fixture.ProductResponseFixture;
import br.com.jardelcell.inventory.inventory.InventoryMovementService;
import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductMapper;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import br.com.jardelcell.inventory.product.dto.CreateProductRequest;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private InventoryMovementService inventoryMovementService;

    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    private CreateProductRequest createRequest() {
        return new CreateProductRequest(
                "SN123456789",
                "356789123456789",
                "Apple",
                "iPhone 15",
                "128GB",
                "Black",
                new BigDecimal("4000.00"),
                new BigDecimal("5500.00")
        );
    }

    @Test
    void shouldCreateProductSuccessfully() {
        CreateProductRequest request = createRequest();
        Product product = ProductFixture.inStock();
        ProductResponse response = ProductResponseFixture.inStock();

        when(productRepository.existsBySerialNumber(request.serialNumber()))
                .thenReturn(false);
        when(productRepository.existsByImei(request.imei()))
                .thenReturn(false);
        when(productMapper.toEntity(request, ProductStatus.IN_STOCK))
                .thenReturn(product);
        when(productMapper.toResponse(product))
                .thenReturn(response);

        ProductResponse result = createProductUseCase.execute(request);

        assertEquals(response, result);

        verify(productRepository)
                .existsBySerialNumber(request.serialNumber());
        verify(productRepository)
                .existsByImei(request.imei());
        verify(productMapper)
                .toEntity(request, ProductStatus.IN_STOCK);
        verify(productRepository)
                .save(product);
        verify(inventoryMovementService)
                .registerEntry(product);
        verify(productMapper)
                .toResponse(product);
    }

    @Test
    void shouldThrowExceptionWhenSerialNumberAlreadyExists() {
        CreateProductRequest request = createRequest();

        when(productRepository.existsBySerialNumber(request.serialNumber()))
                .thenReturn(true);

        assertThrows(
                ProductAlreadyExistsException.class,
                () -> createProductUseCase.execute(request)
        );

        verify(productRepository, never()).existsByImei(any());
        verify(productMapper, never()).toEntity(any(), any());
        verify(productRepository, never()).save(any());
        verify(inventoryMovementService, never()).registerEntry(any());
        verify(productMapper, never()).toResponse(any());
    }

    @Test
    void shouldThrowExceptionWhenImeiAlreadyExists() {
        CreateProductRequest request = createRequest();

        when(productRepository.existsBySerialNumber(request.serialNumber()))
                .thenReturn(false);
        when(productRepository.existsByImei(request.imei()))
                .thenReturn(true);

        assertThrows(
                ProductAlreadyExistsException.class,
                () -> createProductUseCase.execute(request)
        );

        verify(productRepository).existsBySerialNumber(request.serialNumber());
        verify(productRepository).existsByImei(request.imei());

        verify(productMapper, never()).toEntity(any(), any());
        verify(productRepository, never()).save(any());
        verify(inventoryMovementService, never()).registerEntry(any());
        verify(productMapper, never()).toResponse(any());
    }
}