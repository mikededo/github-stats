package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.CommentMapper;
import io.pakland.mdas.githubstats.domain.entity.Comment;
import io.pakland.mdas.githubstats.domain.repository.CommentExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubCommentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

public class CommentGitHubRepository implements CommentExternalRepository {
    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(CommentGitHubRepository.class);

    public CommentGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Comment> fetchCommentsFromPullRequest(
            String repositoryOwner, String repositoryName, Integer pullRequestNumber, Integer page, Integer perPage)
            throws HttpException {

        try {

            return this.webClientConfiguration.getWebClient().get()
                    .uri(String.format("/repos/%s/%s/pulls/%s/comments?%s", repositoryOwner,
                            repositoryName, pullRequestNumber, getRequestParams(page, perPage)))
                    .retrieve()
                    .bodyToFlux(GitHubCommentDTO.class)
                    .map(CommentMapper::dtoToEntity)
                    .collectList()
                    .block();

        } catch (WebClientResponseException ex) {
            logger.error(ex.toString());
            throw new HttpException(ex.getRawStatusCode(), ex.getMessage());
        }
    }

    private String getRequestParams(Integer page, Integer perPage) {
        return String.format("per_page=%d&page=%d", perPage, page < 0 ? 1 : page);
    }

}
