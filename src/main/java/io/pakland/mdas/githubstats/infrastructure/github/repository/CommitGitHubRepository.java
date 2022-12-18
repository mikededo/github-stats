package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Commit;
import io.pakland.mdas.githubstats.domain.repository.CommitExternalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

public class CommitGitHubRepository implements CommitExternalRepository {
    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(CommitGitHubRepository.class);

    public CommitGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Commit> fetchCommitsFromPullRequest(FetchCommitsFromPullRequestRequest request) throws HttpException {

        try {
            return this.webClientConfiguration.getWebClient().get()
                    .uri(String.format("/repos/%s/%s/pulls/%s/commits?%s", request.getRepositoryOwner(),
                            request.getRepositoryName(), request.getPullRequestNumber(), getRequestParams(request)))
                    .retrieve()
                    .bodyToFlux(Commit.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }

    private String getRequestParams(FetchCommitsFromPullRequestRequest request) {
        return String.format("per_page=%d&page=%d", request.getPerPage(), request.getPage() < 0 ? 1 : request.getPage());
    }
}
