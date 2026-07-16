package br.com.jardelcell.inventory.inventory.dto;

import br.com.jardelcell.inventory.inventory.MovementType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record InventoryMovementResponse(
        MovementType type,
        BigDecimal movementPrice,
        String observation,
        OffsetDateTime createdAt,
        String employeeName
) {
}
