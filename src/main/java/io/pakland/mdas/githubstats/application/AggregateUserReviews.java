package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.UserReview;
import io.pakland.mdas.githubstats.domain.UserReviewAggregation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregateUserReviews {

    public AggregateUserReviews() {}

    public UserReviewAggregation execute(List<UserReview> userReviews) {
        return UserReviewAggregation.aggregate(userReviews);
    }

}
