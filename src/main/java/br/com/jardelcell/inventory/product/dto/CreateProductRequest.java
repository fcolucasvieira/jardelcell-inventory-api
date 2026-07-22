package br.com.jardelcell.inventory.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Serial number is required.")
        String serialNumber,

        String imei,

        @NotBlank(message = "Brand is required.")
        String brand,

        @NotBlank(message = "Model is required.")
        String model,

        @NotBlank(message = "Storage is required.")
        String storage,

        @NotBlank(message = "Color is required.")
        String color,

        @NotNull(message = "Purchase price is required.")
        @Positive(message = "Purchase price must be greater than zero.")
        BigDecimal purchasePrice,

        @NotNull(message = "Sale price is required.")
        @Positive(message = "Sale price must be greater than zero.")
        BigDecimal salePrice
) {
}
