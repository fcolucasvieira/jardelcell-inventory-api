package br.com.jardelcell.inventory.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsBySerialNumber(String serialNumber);

    boolean existsByImei(String imei);
}
