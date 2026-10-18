package com.tecylab.order_api.client;

import com.tecylab.order_api.dto.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class ClientClient {
    private final WebClient.Builder webClientBuilder;

    @Value("${client.service.url}")
    private String clientServiceUrl;

    public Mono<ClientResponse> getClientById(Long clientId) {
        WebClient webClient = webClientBuilder.baseUrl(clientServiceUrl).build();

        return webClient
                .get()
                .uri("/{id}", clientId)
                .retrieve()
                .bodyToMono(ClientResponse.class);
    }
}
