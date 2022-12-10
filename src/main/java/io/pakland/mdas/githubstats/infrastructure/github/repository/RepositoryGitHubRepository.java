package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Repository;
import io.pakland.mdas.githubstats.domain.repository.RepositoryExternalRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class RepositoryGitHubRepository implements RepositoryExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(OrganizationGitHubRepository.class);

    public RepositoryGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Repository> fetchTeamRepositories(String organizationLogin, String teamSlug)
        throws HttpException {
        try {
            return this.webClientConfiguration.getWebClient().get()
                .uri(String.format("/organizations/%d/team/%d/repos", organizationLogin, teamSlug))
                    .retrieve()
                    .bodyToFlux(Repository.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
