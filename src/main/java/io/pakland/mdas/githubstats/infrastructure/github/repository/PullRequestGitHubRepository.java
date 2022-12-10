package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.dto.PullRequestDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

public class PullRequestGitHubRepository implements PullRequestExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(PullRequestGitHubRepository.class);

    public PullRequestGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<PullRequestDTO> fetchPullRequestsFromRepository(Integer repositoryOwnerId, Integer repositoryId) throws HttpException {
        try {
            return this.webClientConfiguration.getWebClient().get()
                    .uri(String.format("/organizations/%d/team/%d/repos", repositoryOwnerId, repositoryId))
                    .retrieve()
                    .bodyToFlux(PullRequestDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
