package br.com.jardelcell.inventory.product.dto;

import java.math.BigDecimal;

public record CreateProductRequest(
        String serialNumber,
        String imei,
        String brand,
        String model,
        String storage,
        String color,
        BigDecimal purchasePrice,
        BigDecimal salePrice
) {
}
