package com.tecylab.api_gateway.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdTokenCredentials;
import com.google.auth.oauth2.IdTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyRoutingFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;
import java.util.Set;

@Component
@ConditionalOnProperty(
        prefix = "security.cloud-run-auth",
        name = "enabled",
        havingValue = "true"
)
public class CloudRunAuthFilter implements GlobalFilter, Ordered {

    private final Set<String> allowedAudiences;

    public CloudRunAuthFilter(
            @Value("${services.product-api.uri}") String productUrl,
            @Value("${services.client-api.uri}") String clientUrl) {

        this.allowedAudiences = Set.of(
                normalize(productUrl),
                normalize(clientUrl)
        );
    }

    @Override
    public int getOrder() {
        return NettyRoutingFilter.ORDER - 1;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        URI target = exchange.getAttribute(
                ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR
        );

        if (target == null) {
            return chain.filter(exchange);
        }

        String audience = normalize(
                target.getScheme() + "://" + target.getAuthority()
        );

        if (!allowedAudiences.contains(audience)) {
            return chain.filter(exchange);
        }

        return Mono.fromCallable(() -> {
                    GoogleCredentials credentials =
                            GoogleCredentials.getApplicationDefault();

                    if (!(credentials instanceof IdTokenProvider provider)) {
                        throw new IllegalStateException(
                                "Credentials do not support ID tokens"
                        );
                    }

                    IdTokenCredentials tokenCredentials =
                            IdTokenCredentials.newBuilder()
                                    .setIdTokenProvider(provider)
                                    .setTargetAudience(audience)
                                    .build();

                    tokenCredentials.refreshIfExpired();

                    return tokenCredentials.getIdToken().getTokenValue();
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(token -> {
                    ServerHttpRequest request = exchange.getRequest()
                            .mutate()
                            .headers(headers -> headers.set(
                                    "X-Serverless-Authorization",
                                    "Bearer " + token
                            ))
                            .build();

                    return chain.filter(
                            exchange.mutate().request(request).build()
                    );
                });
    }

    private static String normalize(String url) {
        URI uri = URI.create(url);
        return uri.getScheme() + "://" + uri.getAuthority();
    }
}
