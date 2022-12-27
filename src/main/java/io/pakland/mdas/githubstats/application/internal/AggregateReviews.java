package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.Review;
import io.pakland.mdas.githubstats.domain.entity.ReviewAggregation;
import io.pakland.mdas.githubstats.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AggregateReviews {

    ReviewAggregation reviewAggregation = new ReviewAggregation();
    public AggregateReviews() {}

    public Map<User, ReviewAggregation> execute(List<Review> reviews) {
        // { "userA": [reviewsFromA], "userB": [reviewsFromB] ... }
        Map<User, List<Review>> groupedReviews = reviews.stream()
            .collect(Collectors.groupingBy(Review::getUser));

        // { "userA": aggregatedReviewsFromA, "userB": aggregatedReviewsFromB ... }
        Map<User, ReviewAggregation> groupedAggReviews = new HashMap<>();
        groupedReviews.keySet().forEach(user -> {
            List<Review> reviewsByUser = groupedReviews.get(user);
            groupedAggReviews.put(user, reviewAggregation.aggregate(reviewsByUser));
        });

        return groupedAggReviews;
    }

}
