package io.pakland.mdas.githubstats.infrastructure.github.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Commit;
import io.pakland.mdas.githubstats.domain.PullRequest;
import io.pakland.mdas.githubstats.domain.repository.CommitExternalRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Data
class CommitSha {
    @JsonProperty("sha")
    private String sha;
}

public class CommitGitHubRepository implements CommitExternalRepository {
    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(CommitGitHubRepository.class);

    public CommitGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Commit> fetchCommitsFromPullRequest(FetchCommitsFromPullRequestRequest request) throws HttpException {

        try {

            List<CommitSha> commitsSha = this.webClientConfiguration.getWebClient().get()
                    .uri(String.format("/repos/%s/%s/pulls/%s/commits?%s", request.getRepositoryOwner(),
                            request.getRepositoryName(), request.getPullRequestNumber(), getRequestParams(request)))
                    .retrieve()
                    .bodyToFlux(CommitSha.class)
                    .collectList()
                    .block();

            List<Commit> commits = new ArrayList<>();
            if (commitsSha != null) {
                for (CommitSha commitSha : commitsSha) {
                    commits.add(
                            (Commit) this.webClientConfiguration.getWebClient().get()
                                    .uri(String.format("/repos/%s/%s/commits/%s", request.getRepositoryOwner(),
                                            request.getRepositoryName(), commitSha.getSha()))
                                    .retrieve()
                                    .bodyToMono(Commit.class)
                                    .block()
                    );
                }
            }

            return commits;

        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }

    private String getRequestParams(FetchCommitsFromPullRequestRequest request) {
        return String.format("per_page=%d&page=%d", request.getPerPage(), request.getPage() < 0 ? 1 : request.getPage());
    }
}
