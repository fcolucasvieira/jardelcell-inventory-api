package br.com.jardelcell.inventory.inventory;

import br.com.jardelcell.inventory.common.exception.ResourceNotFoundException;
import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.user.User;
import br.com.jardelcell.inventory.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InventoryMovementService {
    private final InventoryMovementRepository inventoryMovementRepository;
    private final UserRepository userRepository;

    public void registerEntry(Product product) {
        User user = getAuthenticatedUser();

        InventoryMovement movement = new InventoryMovement(
                product,
                user,
                MovementType.ENTRY,
                product.getPurchasePrice(),
                null
        );

        inventoryMovementRepository.save(movement);
    }

    public void registerSale(Product product, BigDecimal movementPrice, String observation) {
        User user = getAuthenticatedUser();

        InventoryMovement movement = new InventoryMovement(
                product,
                user,
                MovementType.SALE,
                movementPrice,
                observation
        );

        inventoryMovementRepository.save(movement);
    }

    public void registerReserve(Product product, String observation) {
        User user = getAuthenticatedUser();

        InventoryMovement movement = new InventoryMovement(
                product,
                user,
                MovementType.RESERVE,
                null,
                observation
        );

        inventoryMovementRepository.save(movement);
    }

    public void registerUnreserve(Product product, String observation) {
        User user = getAuthenticatedUser();

        InventoryMovement movement = new InventoryMovement(
                product,
                user,
                MovementType.UNRESERVE,
                null,
                observation
        );

        inventoryMovementRepository.save(movement);
    }

    public void registerDefective(Product product, String observation) {
        User user = getAuthenticatedUser();

        InventoryMovement movement = new InventoryMovement(
                product,
                user,
                MovementType.DEFECTIVE,
                null,
                observation
        );

        inventoryMovementRepository.save(movement);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with Email: " + email
                        ));
    }
}
