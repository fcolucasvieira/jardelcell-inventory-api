package br.com.jardelcell.inventory.product.dto;

import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record RepairProductRequest(
        @PositiveOrZero(message = "The repair price must be greater than or equal to zero.")
        BigDecimal repairPrice,

        String observation
){}
