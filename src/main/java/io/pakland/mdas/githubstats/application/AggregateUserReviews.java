package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.entity.UserReview;
import io.pakland.mdas.githubstats.domain.entity.UserReviewAggregation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregateUserReviews {

    UserReviewAggregation userReviewAggregation = new UserReviewAggregation();
    public AggregateUserReviews() {}

    public UserReviewAggregation execute(List<UserReview> userReviews) {
        return userReviewAggregation.aggregate(userReviews);
    }

}
