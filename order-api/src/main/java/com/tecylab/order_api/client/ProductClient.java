package com.tecylab.order_api.client;

import com.tecylab.order_api.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductClient {
    private final WebClient.Builder webClientBuilder;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public Mono<ProductResponse> getProductById(Long productId) {
        WebClient webClient = webClientBuilder.baseUrl(productServiceUrl).build();

        return webClient
                .get()
                .uri("/{id}", productId)
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }

    public Mono<List<ProductResponse>> findProductsByIdList(@RequestParam List<Long> ids) {
        WebClient webClient = webClientBuilder.baseUrl(productServiceUrl).build();

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/list").queryParam("ids", String.join(",", ids.stream().map(String::valueOf).toList())).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductResponse>>() {});
    }
}
