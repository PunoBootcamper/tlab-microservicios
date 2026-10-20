package com.tecylab.order_api.controller;

import com.tecylab.order_api.client.ClientClient;
import com.tecylab.order_api.client.ProductClient;
import com.tecylab.order_api.dto.ClientResponse;
import com.tecylab.order_api.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final ClientClient clientClient;
    private final ProductClient productClient;

    @GetMapping("/clients")
    public Mono<ClientResponse> orders() {
        return clientClient.getClientById(1L);
    }

    @GetMapping("/products")
    public Mono<ProductResponse> productById() {
        return  productClient.getProductById(2L);
    }

    @GetMapping("/products/list")
    public Mono<List<ProductResponse>> listProducts() {

        List<Long> ids = List.of(1L, 2L, 3L);
        
        return productClient.findProductsByIdList(ids);
    }
}
