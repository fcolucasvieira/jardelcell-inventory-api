package br.com.jardelcell.inventory.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, UUID> {
    @Query("""
            SELECT m
            FROM InventoryMovement m
            JOIN FETCH m.user
            WHERE m.product.id = :productId
            ORDER BY m.createdAt DESC 
    """)
    List<InventoryMovement> findByProductId(UUID productId);
}
