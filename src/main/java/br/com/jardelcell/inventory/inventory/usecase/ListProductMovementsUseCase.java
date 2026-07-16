package br.com.jardelcell.inventory.inventory.usecase;

import br.com.jardelcell.inventory.common.exception.ResourceNotFoundException;
import br.com.jardelcell.inventory.inventory.InventoryMovement;
import br.com.jardelcell.inventory.inventory.InventoryMovementMapper;
import br.com.jardelcell.inventory.inventory.InventoryMovementRepository;
import br.com.jardelcell.inventory.inventory.dto.InventoryMovementResponse;
import br.com.jardelcell.inventory.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListProductMovementsUseCase {
    private final ProductRepository productRepository;
    private final InventoryMovementRepository inventoryMovementRepository;
    private final InventoryMovementMapper inventoryMovementMapper;

    public List<InventoryMovementResponse> execute(UUID productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with Id: " + productId
                ));

        List<InventoryMovement> movements = inventoryMovementRepository.findByProductId(productId);

        return movements.stream()
                .map(inventoryMovementMapper::toResponse)
                .toList();
    }
}
