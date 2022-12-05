package io.pakland.mdas.githubstats.infrastructure.repository.github;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class GithubRepositoryBase {
    protected final WebClient githubClient;

    public GithubRepositoryBase(WebClient.Builder builder, @Value("${github.api.url}") String githubApiUrl, @Value("${github.api.key}") String githubApiKey) {
        this.githubClient = builder.baseUrl(githubApiUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + githubApiKey);
                })
                .build();
    }
}
