package com.tecylab.product_api.dto;

import com.tecylab.product_api.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank
        @Schema(description = "Name of the product", example = "Laptop")
        String name,

        @Schema(description = "Description of the product", example = "High-performance laptop")
        String description,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
        @Digits(integer = 10, fraction = 2,
                message = "El precio debe tener máximo 10 dígitos enteros y 2 decimales")
        @Schema(description = "Price of the product", example = "999.99")
        BigDecimal price,

        @NotBlank
        @Schema(description = "Stock quantity of the product", example = "10")
        Long stockQuantity,

        @Schema(description = "Status of the product", example = "ACTIVE")
        Status status
) {

}
