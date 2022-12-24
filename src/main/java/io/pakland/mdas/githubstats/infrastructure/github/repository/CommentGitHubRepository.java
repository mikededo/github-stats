package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.CommentMapper;
import io.pakland.mdas.githubstats.domain.entity.Comment;
import io.pakland.mdas.githubstats.domain.lib.InternalCaching;
import io.pakland.mdas.githubstats.domain.repository.CommentExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubCommentDTO;
import java.util.List;
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
    public List<Comment> fetchCommentsFromPullRequest(FetchCommentsFromPullRequestRequest request)
        throws HttpException {
        String query = String.format("/repos/%s/%s/pulls/%s/comments?%s",
            request.getRepositoryOwner(),
            request.getRepositoryName(),
            request.getPullRequestNumber(),
            getRequestParams(request.getPage(), request.getPerPage())
        );

        try {
            logger.info(
                " - Fetching comments from pull request: " + request.getPullRequestNumber());
            List<Comment> result = this.webClientConfiguration.getWebClient().get()
                .uri(query)
                .retrieve()
                .bodyToFlux(GitHubCommentDTO.class)
                .map(CommentMapper::dtoToEntity)
                .collectList()
                .block();
            cache.add(query, result);

            return result;

        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }

    private String getRequestParams(Integer page, Integer perPage) {
        return String.format("per_page=%d&page=%d", perPage, page < 0 ? 1 : page);
    }

}
