package io.pakland.mdas.githubstats.infrastructure.repository.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFlux
public class GithubWebClientConfiguration {

    private static final String GITHUB_API_BASE_URL = "https://api.github.com";

    // TODO: Define your own API Key while configuration option via shell is not available
    private static final String GITHUB_API_KEY = "";
    
    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(GITHUB_API_BASE_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + GITHUB_API_KEY);
                })
                .build();
    }
}
