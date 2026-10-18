package com.tecylab.client_api.dto;

import java.time.LocalDateTime;


public record ErrorDTO(
    String status,
    String title,
    String detail,
    LocalDateTime timestamp,
    String path
    ){}
