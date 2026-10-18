package com.tecylab.product_api.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorDTO(
        String status,
        String title,
        String detail,
        LocalDateTime timestamp,
        String path
){}
