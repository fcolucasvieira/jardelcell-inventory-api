package br.com.jardelcell.inventory.dashboard;

import br.com.jardelcell.inventory.inventory.dto.InventoryMovementResponse;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
        Long productsInStock,
        Long reservedProducts,
        Long soldProducts,
        Long defectiveProducts,
        Long exchangedProducts,
        BigDecimal stockInvestment,
        BigDecimal salesRevenue,
        List<InventoryMovementResponse> lastMovements
) {
}
