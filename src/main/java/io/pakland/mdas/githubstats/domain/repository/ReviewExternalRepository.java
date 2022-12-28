package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Review;

import java.util.List;

public interface ReviewExternalRepository {

    public List<Review> fetchReviewsFromPullRequestByPage(PullRequest request, Integer page) throws HttpException;
}