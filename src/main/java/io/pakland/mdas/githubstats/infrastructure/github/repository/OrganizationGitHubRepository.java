package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.repository.OrganizationExternalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

public class OrganizationGitHubRepository implements OrganizationExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(OrganizationGitHubRepository.class);

    public OrganizationGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Organization> fetchAvailableOrganizations() throws HttpException {
        try {
            return this.webClientConfiguration.getWebClient().get()
                    .uri("/user/orgs")
                    .retrieve()
                    .bodyToFlux(Organization.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
