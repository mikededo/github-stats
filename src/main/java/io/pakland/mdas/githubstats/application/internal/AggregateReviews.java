package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.Review;
import io.pakland.mdas.githubstats.domain.entity.ReviewAggregation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregateReviews {

    ReviewAggregation reviewAggregation = new ReviewAggregation();
    public AggregateReviews() {}

    public ReviewAggregation execute(List<Review> reviews) {
        return reviewAggregation.aggregate(reviews);
    }

}
