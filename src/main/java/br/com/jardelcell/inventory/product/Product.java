package br.com.jardelcell.inventory.product;

import br.com.jardelcell.inventory.common.exception.InvalidProductStatusException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "serial_number", nullable = false, unique = true, length = 50)
    private String serialNumber;

    @Column(unique = true, length = 20)
    private String imei;

    @Column(nullable = false, length = 50)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false, length = 20)
    private String storage;

    @Column(nullable = false, length = 30)
    private String color;

    @Column(name = "purchase_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "sale_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    public Product(String serialNumber, String imei,
                   String brand, String model, String storage, String color,
                   BigDecimal purchasePrice, BigDecimal salePrice,
                   ProductStatus status) {
        this.serialNumber = serialNumber;
        this.imei = imei;
        this.brand = brand;
        this.model = model;
        this.storage = storage;
        this.color = color;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.status = status;
    }

    public void markAsInStock() {
        if(this.status != ProductStatus.RESERVED) {
            throw new InvalidProductStatusException("Only reserved products can returned to stock.");
        }

        this.status = ProductStatus.IN_STOCK;
    }

    public void markAsSold() {
        if(this.status != ProductStatus.IN_STOCK) {
            throw new InvalidProductStatusException("Only products in stock can be sold.");
        }

        this.status = ProductStatus.SOLD;
    }

    public void markAsReserved() {
        if(this.status != ProductStatus.IN_STOCK) {
            throw new InvalidProductStatusException("Only products in stock can be reserved.");
        }

        this.status = ProductStatus.RESERVED;
    }

    public void markAsDefective() {
        if(this.status != ProductStatus.IN_STOCK &&
                this.status != ProductStatus.RESERVED) {
            throw new InvalidProductStatusException(
                    "Only products in stock or reserved can be defective."
            );
        }

        this.status = ProductStatus.DEFECTIVE;
    }
}
