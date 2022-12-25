package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.entity.UserReview;
import io.pakland.mdas.githubstats.domain.repository.ReviewExternalRepository;

import java.util.ArrayList;
import java.util.List;

public class FetchReviewsFromPullRequest {
    private final ReviewExternalRepository reviewExternalRepository;

    public FetchReviewsFromPullRequest(ReviewExternalRepository reviewExternalRepository) {
        this.reviewExternalRepository = reviewExternalRepository;
    }

    public List<UserReview> execute(PullRequest pullRequest)
            throws HttpException {
        int page = 1, responseResults = 0;
        List<UserReview> reviewList = new ArrayList<>();
        Repository repository = pullRequest.getRepository();

        do {
            ReviewExternalRepository.FetchReviewsFromPullRequestRequest request = ReviewExternalRepository.FetchReviewsFromPullRequestRequest.builder()
                    .repositoryOwner(repository.getOwnerLogin())
                    .repositoryName(repository.getName())
                    .pullRequestNumber(pullRequest.getNumber())
                    .page(page)
                    .perPage(100)
                    .build();
            List<UserReview> apiResults = this.reviewExternalRepository.fetchReviewsFromPullRequest(request);
            reviewList.addAll(apiResults);
            responseResults = apiResults.size();
            page++;
        } while (responseResults > 0);

        return reviewList;
    }
}
