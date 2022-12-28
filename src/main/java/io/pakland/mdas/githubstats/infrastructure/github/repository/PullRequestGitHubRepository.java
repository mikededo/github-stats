package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.PullRequestMapper;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.enums.PullRequestState;
import io.pakland.mdas.githubstats.domain.lib.InternalCaching;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubPageablePullRequestRequest;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubPullRequestDTO;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class PullRequestGitHubRepository implements PullRequestExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(PullRequestGitHubRepository.class);

    private final InternalCaching<String, List<PullRequest>> cache = new InternalCaching<>();

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
        List<PullRequest> maybeResult = cache.get(uri);
        if (maybeResult != null) {
            return maybeResult;
        }

        try {
            // We need to fetch all pull requests as, even though a pull request may have been
            // created out of the date range given by the user, such pr may contain commits, comments
            // or reviews that are within the range specified by the user
            List<PullRequest> result = this.webClientConfiguration.getWebClient().get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubPullRequestDTO.class)
                .parallel()
                .filter(pr -> Objects.nonNull(pr.getUser()))
                .map(PullRequestMapper::dtoToEntity)
                .sequential()
                .collectList()
                .block();
            cache.add(uri, result);

            return result;
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }
}
