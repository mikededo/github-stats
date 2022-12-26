package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public interface ReviewExternalRepository {

    public List<Review> fetchReviewsFromPullRequest(FetchReviewsFromPullRequestRequest request) throws HttpException;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class FetchReviewsFromPullRequestRequest {
        private String repositoryOwner;
        private String repositoryName;
        private Integer pullRequestNumber;
        private Integer page;
        private Integer perPage;
    }
}