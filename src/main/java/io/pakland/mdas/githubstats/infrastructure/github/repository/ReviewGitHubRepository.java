package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.ReviewMapper;
import io.pakland.mdas.githubstats.domain.entity.UserReview;
import io.pakland.mdas.githubstats.domain.lib.InternalCaching;
import io.pakland.mdas.githubstats.domain.repository.ReviewExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubReviewDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class ReviewGitHubRepository implements ReviewExternalRepository {

    private final WebClientConfiguration webClientConfiguration;
    private final Logger logger = LoggerFactory.getLogger(ReviewGitHubRepository.class);

    private final InternalCaching<String, List<UserReview>> cache = new InternalCaching<>();

    public ReviewGitHubRepository(WebClientConfiguration webClientConfiguration) {
        this.webClientConfiguration = webClientConfiguration;
    }

    @Override
    public List<UserReview> fetchReviewsFromPullRequest(FetchReviewsFromPullRequestRequest request)
        throws HttpException {
        String query = String.format("/repos/%s/%s/pulls/%s/reviews?%s",
            request.getRepositoryOwner(),
            request.getRepositoryName(),
            request.getPullRequestNumber(),
            getRequestParams(request.getPage(), request.getPerPage())
        );
        List<UserReview> maybeResult = cache.get(query);
        if (maybeResult != null) {
            return maybeResult;
        }

        try {

            List<UserReview> result = this.webClientConfiguration.getWebClient().get()
                .uri(query)
                .retrieve()
                .bodyToFlux(GitHubReviewDTO.class)
                .map(ReviewMapper::dtoToEntity)
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
