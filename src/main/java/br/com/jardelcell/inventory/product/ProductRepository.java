package br.com.jardelcell.inventory.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsBySerialNumber(String serialNumber);

    boolean existsByImei(String imei);

    long countByStatus(ProductStatus status);

    @Query("""
    SELECT SUM(p.purchasePrice)
    FROM Product p
    WHERE p.status IN :statuses
    """)
    BigDecimal sumPurchasePriceByStatusIn(List<ProductStatus> statuses);

    @Query("""
    SELECT SUM(p.salePrice)
    FROM Product p
    WHERE p.status IN :statuses
    """)
    BigDecimal sumSalePriceByStatusIn(List<ProductStatus> statuses);
}
