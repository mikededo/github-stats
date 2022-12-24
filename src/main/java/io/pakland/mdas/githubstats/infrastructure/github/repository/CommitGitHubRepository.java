package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.CommitMapper;
import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.repository.CommitExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubCommitDTO;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class CommitGitHubRepository implements CommitExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(CommitGitHubRepository.class);

    private final Map<Integer, Integer> internalQueryCache = new HashMap<>();
    private final List<List<Commit>> commitStore = new ArrayList<>();

    public CommitGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Commit> fetchCommitsFromPullRequest(FetchCommitsFromPullRequestRequest request)
        throws HttpException {
        String query = String.format("/repos/%s/%s/pulls/%s/commits?%s",
            request.getRepositoryOwner(),
            request.getRepositoryName(), request.getPullRequestNumber(),
            getRequestParams(request));
        Integer resultPosition = internalQueryCache.get(query.hashCode());
        if (resultPosition != null) {
            return commitStore.get(resultPosition);
        }

        try {
            logger.info(" - Fetching commits for pull request: " + request.getPullRequestNumber()
                .toString());
            List<Commit> result = this.webClientConfiguration.getWebClient().get()
                .uri(query)
                .retrieve()
                .bodyToFlux(GitHubCommitDTO.class)
                .map(CommitMapper::dtoToEntity)
                .collectList()
                .block();
            commitStore.add(result);
            internalQueryCache.put(query.hashCode(), commitStore.size() - 1);

            return result;
        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }

    private String getRequestParams(FetchCommitsFromPullRequestRequest request) {
        return String.format("per_page=%d&page=%d", request.getPerPage(),
            request.getPage() < 0 ? 1 : request.getPage());
    }
}
