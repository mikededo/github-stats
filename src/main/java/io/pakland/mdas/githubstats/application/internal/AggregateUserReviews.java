package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.Review;
import io.pakland.mdas.githubstats.domain.entity.UserReviewAggregation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregateUserReviews {

    UserReviewAggregation userReviewAggregation = new UserReviewAggregation();
    public AggregateUserReviews() {}

    public UserReviewAggregation execute(List<Review> reviews) {
        return userReviewAggregation.aggregate(reviews);
    }

}
