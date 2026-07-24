package br.com.jardelcell.inventory.dashboard;

import br.com.jardelcell.inventory.inventory.InventoryMovementMapper;
import br.com.jardelcell.inventory.inventory.InventoryMovementRepository;
import br.com.jardelcell.inventory.inventory.MovementType;
import br.com.jardelcell.inventory.inventory.dto.InventoryMovementResponse;
import br.com.jardelcell.inventory.product.ProductRepository;
import br.com.jardelcell.inventory.product.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ProductRepository productRepository;
    private final InventoryMovementRepository inventoryMovementRepository;
    private final InventoryMovementMapper inventoryMovementMapper;

    private static final List<ProductStatus> STOCK_STATUSES =
            List.of(
                    ProductStatus.IN_STOCK,
                    ProductStatus.RESERVED
            );

    private static final List<MovementType> REVENUE_MOVEMENTS =
            List.of(
                    MovementType.SALE,
                    MovementType.EXCHANGE
            );

    public DashboardResponse getDashboard() {
        long inStock = productRepository.countByStatus(ProductStatus.IN_STOCK);
        long reserved = productRepository.countByStatus(ProductStatus.RESERVED);
        long sold = productRepository.countByStatus(ProductStatus.SOLD);
        long defective = productRepository.countByStatus(ProductStatus.DEFECTIVE);
        long exchanged = productRepository.countByStatus(ProductStatus.EXCHANGED);

        BigDecimal stockInvestment = Optional.ofNullable(
                productRepository.sumPurchasePriceByStatusIn(STOCK_STATUSES))
                .orElse(BigDecimal.ZERO);

        BigDecimal salesRevenue = Optional.ofNullable(
                inventoryMovementRepository.sumMovementPriceByTypeIn(REVENUE_MOVEMENTS))
                .orElse(BigDecimal.ZERO);

        List<InventoryMovementResponse> lastMovements = inventoryMovementRepository
                .findLatest(PageRequest.of(0, 5))
                        .stream()
                        .map(inventoryMovementMapper::toResponse)
                        .toList();

        return new DashboardResponse(
                inStock, reserved, sold, defective, exchanged,
                stockInvestment, salesRevenue,
                lastMovements
        );
    }
}
