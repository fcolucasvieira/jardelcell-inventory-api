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
import org.springframework.data.domain.PageRequest;

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

    private static final List<ProductStatus> INVENTORY_VALUE_STATUSES  =
            List.of(ProductStatus.IN_STOCK, ProductStatus.RESERVED);

    private static final List<MovementType> REVENUE_MOVEMENT_TYPES =
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

        when(productRepository.sumPurchasePriceByStatusIn(INVENTORY_VALUE_STATUSES))
                .thenReturn(new BigDecimal("35000.00"));
        when(productRepository.sumSalePriceByStatusIn(INVENTORY_VALUE_STATUSES))
                .thenReturn(new BigDecimal("50000.00"));
        when(inventoryMovementRepository.sumMovementPriceByTypeIn(REVENUE_MOVEMENT_TYPES))
                .thenReturn(new BigDecimal("67000.00"));
        when(inventoryMovementRepository.sumMovementPriceByTypeIn(List.of(MovementType.REPAIR)))
                .thenReturn(new BigDecimal("350.00"));
        when(productRepository.sumPurchasePriceByStatusIn(List.of(ProductStatus.DEFECTIVE)))
                .thenReturn(new BigDecimal("1200.00"));

        when(inventoryMovementRepository.findLatest(PageRequest.of(0, 5)))
                .thenReturn(List.of(movement));
        when(inventoryMovementMapper.toResponse(movement))
                .thenReturn(movementResponse);

        DashboardResponse result = dashboardService.getDashboard();

        assertNotNull(result);

        assertEquals(5L, result.inStock());
        assertEquals(2L, result.reserved());
        assertEquals(8L, result.sold());
        assertEquals(1L, result.defective());
        assertEquals(3L, result.exchanged());

        assertEquals(new BigDecimal("35000.00"), result.stockInvestment());
        assertEquals(new BigDecimal("50000.00"), result.projectedRevenue());
        assertEquals(new BigDecimal("67000.00"), result.salesRevenue());
        assertEquals(new BigDecimal("350.00"), result.repairCosts());
        assertEquals(new BigDecimal("1200.00"), result.defectiveLoss());

        assertEquals(1, result.lastMovements().size());

        verify(productRepository).countByStatus(ProductStatus.IN_STOCK);
        verify(productRepository).countByStatus(ProductStatus.RESERVED);
        verify(productRepository).countByStatus(ProductStatus.SOLD);
        verify(productRepository).countByStatus(ProductStatus.DEFECTIVE);
        verify(productRepository).countByStatus(ProductStatus.EXCHANGED);

        verify(productRepository).sumPurchasePriceByStatusIn(INVENTORY_VALUE_STATUSES);
        verify(productRepository).sumSalePriceByStatusIn(INVENTORY_VALUE_STATUSES);
        verify(inventoryMovementRepository).sumMovementPriceByTypeIn(REVENUE_MOVEMENT_TYPES);
        verify(inventoryMovementRepository).sumMovementPriceByTypeIn(List.of(MovementType.REPAIR));
        verify(productRepository).sumPurchasePriceByStatusIn(List.of(ProductStatus.DEFECTIVE));

        verify(inventoryMovementRepository).findLatest(PageRequest.of(0, 5));

        verify(inventoryMovementMapper).toResponse(movement);
    }
}