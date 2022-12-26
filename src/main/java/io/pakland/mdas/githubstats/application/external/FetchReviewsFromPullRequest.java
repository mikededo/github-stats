package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Review;
import io.pakland.mdas.githubstats.domain.repository.ReviewExternalRepository;

import java.util.ArrayList;
import java.util.List;

public class FetchReviewsFromPullRequest {
    private final ReviewExternalRepository reviewExternalRepository;

    public FetchReviewsFromPullRequest(ReviewExternalRepository reviewExternalRepository) {
        this.reviewExternalRepository = reviewExternalRepository;
    }

    public List<Review> execute(PullRequest pullRequest)
        throws HttpException {
        int page = 1;
        List<Review> reviewList = new ArrayList<>();
        int responseResults;
        do {
            List<Review> apiResults = this.reviewExternalRepository.fetchReviewsFromPullRequestByPage(pullRequest, page);
            reviewList.addAll(apiResults);
            responseResults = apiResults.size();
            page++;
        } while (responseResults > 0);

        return reviewList;
    }
}
