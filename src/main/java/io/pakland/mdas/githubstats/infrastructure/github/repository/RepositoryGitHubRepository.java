package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
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
    public List<RepositoryDTO> fetchTeamRepositories(Integer organizationId, Integer teamId)
        throws HttpException {
        try {
            return this.webClientConfiguration.getWebClient().get()
                .uri(String.format("/organizations/%d/team/%d/repos", organizationId, teamId))
                    .retrieve()
                    .bodyToFlux(RepositoryDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
