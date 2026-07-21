package br.com.jardelcell.inventory.dashboard;

import br.com.jardelcell.inventory.inventory.dto.InventoryMovementResponse;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
        Integer productsInStock,
        Integer reservedProducts,
        Integer soldProducts,
        Integer defectiveProducts,
        Integer exchangedProducts,

        BigDecimal stockInvestment,
        BigDecimal salesRevenue,

        List<InventoryMovementResponse> lastMovements
) {
}
