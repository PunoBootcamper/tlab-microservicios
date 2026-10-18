package com.tecylab.order_api.dto;

public record ClientResponse(

        Long id,
        String name,
        String lastName,
        String email,
        String status
) {
}