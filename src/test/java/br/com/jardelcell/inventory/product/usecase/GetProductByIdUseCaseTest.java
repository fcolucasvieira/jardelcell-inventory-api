package br.com.jardelcell.inventory.product.usecase;

import br.com.jardelcell.inventory.common.exception.ResourceNotFoundException;
import br.com.jardelcell.inventory.fixture.ProductFixture;
import br.com.jardelcell.inventory.fixture.ProductResponseFixture;
import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.product.ProductMapper;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import br.com.jardelcell.inventory.product.dto.ProductResponse;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductByIdUseCaseTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private GetProductByIdUseCase getProductByIdUseCase;

    @Test
    void shouldGetProductByIdSuccessfully() {
        UUID id = UUID.randomUUID();

        Product product = ProductFixture.inStock();

        ProductResponse response = ProductResponseFixture.inStock();

        when(productRepository.findById(id))
                .thenReturn(Optional.of(product));
        when(productMapper.toResponse(product))
                .thenReturn(response);

        ProductResponse result = getProductByIdUseCase.execute(id);

        assertEquals(response, result);

        verify(productRepository).findById(id);
        verify(productMapper).toResponse(product);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        UUID id = UUID.randomUUID();

        when(productRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> getProductByIdUseCase.execute(id)
        );

        verify(productRepository).findById(id);

        verify(productMapper, never()).toResponse(any());
    }
}