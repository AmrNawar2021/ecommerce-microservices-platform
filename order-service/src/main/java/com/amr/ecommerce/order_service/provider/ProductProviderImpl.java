package com.amr.ecommerce.order_service.provider;


import com.amr.ecommerce.order_service.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ProductProviderImpl implements ProductProvider {

    private final WebClient.Builder webClientBuilder;

    @Value("${service.product.url}")
    private String productServiceUrl;

    @Override
    public ProductResponse getProductById(Long productId, String jwtToken) {
        return webClientBuilder.build()
                .get()
                .uri(productServiceUrl + "/api/products/{id}", productId)
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();
    }
}
