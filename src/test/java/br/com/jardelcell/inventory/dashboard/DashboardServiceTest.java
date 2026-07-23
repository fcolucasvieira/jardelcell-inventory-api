package br.com.jardelcell.inventory.dashboard;

import br.com.jardelcell.inventory.inventory.InventoryMovement;
import br.com.jardelcell.inventory.inventory.InventoryMovementMapper;
import br.com.jardelcell.inventory.inventory.InventoryMovementRepository;
import br.com.jardelcell.inventory.inventory.MovementType;
import br.com.jardelcell.inventory.inventory.dto.InventoryMovementResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private InventoryMovementRepository inventoryMovementRepository;
    @Mock
    private InventoryMovementMapper inventoryMovementMapper;

    @InjectMocks
    private DashboardService dashboardService;

    private static final List<ProductStatus> STOCK_STATUSES =
            List.of(ProductStatus.IN_STOCK, ProductStatus.RESERVED);

    private static final List<MovementType> REVENUE_MOVEMENTS =
            List.of(MovementType.SALE, MovementType.EXCHANGE);

    @Test
    void shouldGetDashboardSuccessfully() {
        InventoryMovement movement = mock(InventoryMovement.class);

        InventoryMovementResponse movementResponse =
                new InventoryMovementResponse(
                        MovementType.SALE,
                        new BigDecimal("4500.00"),
                        "Product sold for R$ 4500",
                        OffsetDateTime.now(),
                        "Lucas"
                );

        when(productRepository.countByStatus(ProductStatus.IN_STOCK)).thenReturn(5L);
        when(productRepository.countByStatus(ProductStatus.RESERVED)).thenReturn(2L);
        when(productRepository.countByStatus(ProductStatus.SOLD)).thenReturn(8L);
        when(productRepository.countByStatus(ProductStatus.DEFECTIVE)).thenReturn(1L);
        when(productRepository.countByStatus(ProductStatus.EXCHANGED)).thenReturn(3L);

        when(productRepository.sumPurchasePriceByStatusIn(STOCK_STATUSES))
                .thenReturn(new BigDecimal("35000.00"));
        when(inventoryMovementRepository.sumMovementPriceByTypeIn(REVENUE_MOVEMENTS))
                .thenReturn(new BigDecimal("67000.00"));

        when(inventoryMovementRepository.findTop5ByOrderByCreatedAtDesc())
                .thenReturn(List.of(movement));
        when(inventoryMovementMapper.toResponse(movement))
                .thenReturn(movementResponse);

        DashboardResponse result = dashboardService.getDashboard();

        assertNotNull(result);

        // Just a check to query countByStatus (avoiding boilerplate)
        assertEquals(5L, result.productsInStock());

        assertEquals(new BigDecimal("35000.00"), result.stockInvestment());
        assertEquals(new BigDecimal("67000.00"), result.salesRevenue());

        assertEquals(1, result.lastMovements().size());

        verify(productRepository).countByStatus(ProductStatus.IN_STOCK);
        verify(productRepository).countByStatus(ProductStatus.RESERVED);
        verify(productRepository).countByStatus(ProductStatus.SOLD);
        verify(productRepository).countByStatus(ProductStatus.DEFECTIVE);
        verify(productRepository).countByStatus(ProductStatus.EXCHANGED);

        verify(productRepository).sumPurchasePriceByStatusIn(STOCK_STATUSES);
        verify(inventoryMovementRepository).sumMovementPriceByTypeIn(REVENUE_MOVEMENTS);

        verify(inventoryMovementRepository).findTop5ByOrderByCreatedAtDesc();

        verify(inventoryMovementMapper).toResponse(movement);
    }
}