package br.com.jardelcell.inventory.inventory;

import br.com.jardelcell.inventory.product.Product;
import br.com.jardelcell.inventory.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "inventory_movements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",  nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;

    @Column(name = "movement_price", precision = 10, scale = 2)
    private BigDecimal movementPrice;

    @Column(length = 500)
    private String observation;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    public InventoryMovement(Product product, User user,
                             MovementType type, BigDecimal movementPrice,
                             String observation) {
        this.product = product;
        this.user = user;
        this.type = type;
        this.movementPrice = movementPrice;
        this.observation = observation;
    }
}
