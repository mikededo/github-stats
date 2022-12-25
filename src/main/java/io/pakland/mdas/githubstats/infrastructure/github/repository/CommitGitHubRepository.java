package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.CommitMapper;
import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.lib.InternalCaching;
import io.pakland.mdas.githubstats.domain.repository.CommitExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubCommitDTO;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class CommitGitHubRepository implements CommitExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(CommitGitHubRepository.class);

    private final InternalCaching<String, List<Commit>> cache = new InternalCaching<>();

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
        List<Commit> maybeResult = cache.get(query);
        if (maybeResult != null) {
            return maybeResult;
        }

        try {
            logger.info(" - Fetching commits for pull request: " + request.getPullRequestNumber()
                .toString());
            List<Commit> result = this.webClientConfiguration.getWebClient().get()
                .uri(query)
                .retrieve()
                .bodyToFlux(GitHubCommitDTO.class)
                .parallel()
                .filter(dto -> Objects.nonNull(dto.getUser()))
                .map(CommitMapper::dtoToEntity)
                .sequential()
                .collectList()
                .block();
            cache.add(query, result);

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
