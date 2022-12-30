package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AggregateReviews {

    public AggregateReviews() {
    }

    private static Map<Team, Map<User, List<Review>>> groupUserReviewsByTeam(List<Review> reviews) {
        return reviews
            .stream()
            .collect(
                Collectors.groupingBy(review -> review.getUser().getTeam(),
                    Collectors.groupingBy(Review::getUser)));
    }

    private static Map<User, ReviewAggregation> groupReviewAggregationsByUser(
        Map<User, List<Review>> userReviewMap) {
        Map<User, ReviewAggregation> aggReviewsByUser = new HashMap<>();
        userReviewMap.forEach((user, reviewList) ->
            aggReviewsByUser.put(user, ReviewAggregation.aggregate(reviewList))
        );
        return aggReviewsByUser;
    }

    public Map<Team, Map<User, ReviewAggregation>> execute(List<Review> reviews) {
        Map<Team, Map<User, List<Review>>> groupedReviews = groupUserReviewsByTeam(reviews);

        Map<Team, Map<User, ReviewAggregation>> aggReviews = new HashMap<>();
        groupedReviews.forEach((team, userReviewMap) ->
            aggReviews.put(team, groupReviewAggregationsByUser(userReviewMap)));

        return aggReviews;
    }

}
