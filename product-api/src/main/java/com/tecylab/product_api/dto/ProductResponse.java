package com.tecylab.product_api.dto;

import com.tecylab.product_api.model.Status;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Status status
) {
}
