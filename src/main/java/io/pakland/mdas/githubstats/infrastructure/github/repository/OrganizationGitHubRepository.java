package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.OrganizationMapper;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.repository.OrganizationExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubOrganizationDTO;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class OrganizationGitHubRepository implements OrganizationExternalRepository {

    private final WebClientConfiguration webClientConfiguration;

    public OrganizationGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Organization> fetchAvailableOrganizations() throws HttpException {
        final String uri = "/user/orgs";

        try {
            return this.webClientConfiguration.getWebClient().get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubOrganizationDTO.class)
                .map(OrganizationMapper::dtoToEntity)
                .collectList()
                .block();
        } catch (WebClientResponseException ex) {
            System.err.println(ex);
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
