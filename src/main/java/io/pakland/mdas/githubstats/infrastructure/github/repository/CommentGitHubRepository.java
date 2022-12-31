package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.CommentMapper;
import io.pakland.mdas.githubstats.domain.Comment;
import io.pakland.mdas.githubstats.domain.PullRequest;
import io.pakland.mdas.githubstats.domain.repository.CommentExternalRepository;
import io.pakland.mdas.githubstats.domain.utils.InternalCaching;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubCommentDTO;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubPageableRequest;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class CommentGitHubRepository implements CommentExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(CommentGitHubRepository.class);

    private final InternalCaching<String, List<Comment>> cache = new InternalCaching<>();

    public CommentGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Comment> fetchCommentsFromPullRequestByPage(PullRequest pullRequest, Integer page)
        throws HttpException {
        final String uri = String.format("/repos/%s/%s/pulls/%s/comments?%s",
            pullRequest.getRepository().getOwnerLogin(),
            pullRequest.getRepository().getName(),
            pullRequest.getNumber(),
            new GitHubPageableRequest(page, 100).getRequestUriWithParameters()
        );
        List<Comment> maybeResult = cache.get(uri);
        if (maybeResult != null) {
            return maybeResult;
        }

        try {
            List<Comment> result = this.webClientConfiguration.getWebClient().get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubCommentDTO.class)
                .parallel()
                .filter(comment -> Objects.nonNull(comment.getCreatedAt())
                    && Objects.nonNull(comment.getUser()))
                .map(CommentMapper::dtoToEntity)
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
