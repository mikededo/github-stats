package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.PullRequestMapper;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.enums.PullRequestState;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubPageablePullRequestRequest;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubPullRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PullRequestGitHubRepository implements PullRequestExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(PullRequestGitHubRepository.class);

    public PullRequestGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<PullRequest> fetchPullRequestsFromRepositoryByPeriodAndPage(
        Repository repository, Date from, Date to, Integer page) throws HttpException {
        final String uri = String.format("/repos/%s/%s/pulls?%s",
            repository.getOwnerLogin(),
            repository.getName(),
            new GitHubPageablePullRequestRequest(PullRequestState.ALL, page, 100)
                .getRequestUriWithParameters()
        );

        try {
            // TODO: Pending to implement the fetch by period. Currently is fetching everything.
            return this.webClientConfiguration.getWebClient().get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubPullRequestDTO.class)
                .parallel()
                .filter(pr -> Objects.nonNull(pr.getUser()))
                .map(PullRequestMapper::dtoToEntity)
                .sequential()
                .collectList()
                .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
