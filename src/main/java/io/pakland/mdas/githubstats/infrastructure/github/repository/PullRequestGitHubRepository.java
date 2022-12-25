package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.PullRequestMapper;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.repository.PullRequestExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubPullRequestDTO;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class PullRequestGitHubRepository implements PullRequestExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(PullRequestGitHubRepository.class);

    public PullRequestGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<PullRequest> fetchPullRequestsFromRepository(
        FetchPullRequestFromRepositoryRequest request) throws HttpException {
        try {
            logger.info(
                " - Fetching pull requests from repository: " + request.getRepositoryOwner() + "/"
                    + request.getRepository());
            return this.webClientConfiguration.getWebClient().get()
                .uri(String.format("/repos/%s/%s/pulls?%s", request.getRepositoryOwner(),
                    request.getRepository(), getRequestParams(request)))
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

    private String getRequestParams(FetchPullRequestFromRepositoryRequest request) {
        return String.format(
            "state=%s&per_page=%d&page=%d", request.getState(), request.getPerPage(),
            request.getPage() < 0 ? 1 : request.getPage()
        );
    }
}
