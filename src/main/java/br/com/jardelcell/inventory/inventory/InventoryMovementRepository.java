package br.com.jardelcell.inventory.inventory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, UUID> {
    @Query("""
            SELECT im
            FROM InventoryMovement im
            JOIN FETCH im.user
            WHERE im.product.id = :productId
            ORDER BY im.createdAt DESC 
    """)
    List<InventoryMovement> findByProductId(UUID productId);

    @Query("""
    SELECT SUM(im.movementPrice)
    FROM InventoryMovement im
    WHERE im.type IN :types
    """
    )
    BigDecimal sumMovementPriceByTypeIn(List<MovementType> types);

    @Query("""
    SELECT im
    FROM InventoryMovement im
    JOIN FETCH im.user
    ORDER BY im.createdAt DESC
    """
    )
    List<InventoryMovement> findLatest(Pageable pageable);
}
