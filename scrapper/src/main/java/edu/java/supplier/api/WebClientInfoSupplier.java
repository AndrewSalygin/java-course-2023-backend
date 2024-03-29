package edu.java.supplier.api;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class WebClientInfoSupplier implements InfoSupplier {
    private final WebClient webClient;

    public WebClientInfoSupplier(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public <T> T executeRequestGet(String uri, Class<T> type, T defaultValue) {
        return webClient.get().uri(uri).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(type)
            .onErrorReturn(defaultValue).block();
    }
}
