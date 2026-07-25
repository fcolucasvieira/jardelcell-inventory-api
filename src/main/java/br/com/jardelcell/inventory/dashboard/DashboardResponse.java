package br.com.jardelcell.inventory.dashboard;

import br.com.jardelcell.inventory.inventory.dto.InventoryMovementResponse;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
        Long inStock,
        Long reserved,
        Long sold,
        Long defective,
        Long exchanged,
        BigDecimal stockInvestment,
        BigDecimal projectedRevenue,
        BigDecimal salesRevenue,
        BigDecimal repairCosts,
        BigDecimal defectiveLoss,
        List<InventoryMovementResponse> lastMovements
) {
}
