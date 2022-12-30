package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.PullRequestMapper;
import io.pakland.mdas.githubstats.domain.DateRange;
import io.pakland.mdas.githubstats.domain.PullRequest;
import io.pakland.mdas.githubstats.domain.PullRequestState;
import io.pakland.mdas.githubstats.domain.Repository;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository;
import io.pakland.mdas.githubstats.domain.utils.InternalCaching;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubPageablePullRequestRequest;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubPullRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        try {
            final String prListUri = String.format("/repos/%s/%s/pulls?%s",
                repository.getOwnerLogin(),
                repository.getName(),
                new GitHubPageablePullRequestRequest(PullRequestState.ALL, page, 100)
                    .getRequestUriWithParameters()
            );
            List<PullRequest> maybeResult = cache.get(prListUri);
            if (maybeResult != null) {
                return maybeResult;
            }
            // We need to fetch all pull requests as, even though a pull request may have been
            // created out of the date range given by the user, such pr may contain commits, comments
            // or reviews that are within the range specified by the user
            List<PullRequest> result = this.webClientConfiguration.getWebClient().get()
                .uri(prListUri)
                .retrieve()
                .bodyToFlux(GitHubPullRequestDTO.class)
                .parallel()
                .filter(pr -> {
                    boolean isNull = Objects.isNull(pr.getUser());
                    boolean isInRange = DateRange.builder().from(from.toInstant()).to(to.toInstant()).build().isBetweenRange(pr.getCreatedAt());
                    return !isNull && isInRange;
                })
                .flatMap(pr -> fetchPullRequestDetail(repository, pr.getNumber()))
                .map(PullRequestMapper::dtoToEntity)
                .sequential()
                .collectList()
                .block();
            cache.add(prListUri, result);

            return result;
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }

    private Mono<GitHubPullRequestDTO> fetchPullRequestDetail(Repository repository, Integer prNumber) {
        final String prDetailUri = String.format("https://api.github.com/repos/%s/%s/pulls/%s", repository.getOwnerLogin(),
            repository.getName(), prNumber);
        return this.webClientConfiguration.getWebClient().get()
            .uri(prDetailUri)
            .retrieve()
            .bodyToMono(GitHubPullRequestDTO.class);
    }
}
