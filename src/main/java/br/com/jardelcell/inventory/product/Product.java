package br.com.jardelcell.inventory.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(nullable = false, unique = true, length = 50)
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

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    // TODO: In the future, Product should always be created with IN_STOCK.
    // The status will be defined by the domain instead of being received as a constructor parameter.
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
}
