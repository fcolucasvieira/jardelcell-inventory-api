package br.com.jardelcell.inventory.inventory;

import br.com.jardelcell.inventory.inventory.dto.InventoryMovementResponse;
import org.springframework.stereotype.Component;

@Component
public class InventoryMovementMapper {
    public InventoryMovementResponse toResponse(InventoryMovement movement) {
        return new InventoryMovementResponse(
                movement.getType(),
                movement.getMovementPrice(),
                movement.getObservation(),
                movement.getCreatedAt(),
                movement.getUser().getFullName()
        );
    }
}
