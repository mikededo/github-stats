package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.CommentMapper;
import io.pakland.mdas.githubstats.domain.entity.Comment;
import io.pakland.mdas.githubstats.domain.repository.CommentExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubCommentDTO;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class CommentGitHubRepository implements CommentExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(CommentGitHubRepository.class);

    private final Map<Integer, Integer> internalQueryCache = new HashMap<>();
    private final List<List<Comment>> commentStore = new ArrayList<>();

    public CommentGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<Comment> fetchCommentsFromPullRequest(
        String repositoryOwner, String repositoryName, Integer pullRequestNumber, Integer page,
        Integer perPage)
        throws HttpException {
        String query = String.format("/repos/%s/%s/pulls/%s/comments?%s", repositoryOwner,
            repositoryName, pullRequestNumber, getRequestParams(page, perPage));
        Integer dataPosition = internalQueryCache.get(query.hashCode());
        if (dataPosition != null) {
            return commentStore.get(dataPosition);
        }

        try {
            logger.info(" - Fetching comments from pull request: " + pullRequestNumber);
            List<Comment> result = this.webClientConfiguration.getWebClient().get()
                .uri(query)
                .retrieve()
                .bodyToFlux(GitHubCommentDTO.class)
                .map(CommentMapper::dtoToEntity)
                .collectList()
                .block();
            commentStore.add(result);
            internalQueryCache.put(query.hashCode(), commentStore.size() - 1);

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
