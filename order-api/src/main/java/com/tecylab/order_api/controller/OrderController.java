package com.tecylab.order_api.controller;

import com.tecylab.order_api.client.ClientClient;
import com.tecylab.order_api.dto.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final ClientClient clientClient;

    @GetMapping
    public Mono<ClientResponse> orders() {
        return clientClient.getClientById(1L);
    }
}
