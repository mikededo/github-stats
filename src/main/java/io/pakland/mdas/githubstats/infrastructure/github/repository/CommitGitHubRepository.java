package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.CommitMapper;
import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.lib.InternalCaching;
import io.pakland.mdas.githubstats.domain.repository.CommitExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubCommitDTO;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubPageableRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Objects;

public class CommitGitHubRepository implements CommitExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(CommitGitHubRepository.class);

    private final InternalCaching<String, List<Commit>> cache = new InternalCaching<>();

    public CommitGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Commit> fetchCommitsFromPullRequestByPage(PullRequest pullRequest, Integer page)
        throws HttpException {
        final String uri = String.format("/repos/%s/%s/pulls/%s/reviews?%s",
            pullRequest.getRepository().getOwnerLogin(),
            pullRequest.getRepository().getName(),
            pullRequest.getNumber(),
            new GitHubPageableRequest(page, 100).getRequestUriWithParameters()
        );

        List<Commit> maybeResult = cache.get(uri);
        if (maybeResult != null) {
            return maybeResult;
        }

        try {
            logger.info(" - Fetching commits for pull request: " + pullRequest.getNumber().toString());
            List<Commit> result = this.webClientConfiguration.getWebClient().get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubCommitDTO.class)
                .parallel()
                .filter(dto -> Objects.nonNull(dto.getUser()))
                .map(CommitMapper::dtoToEntity)
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
