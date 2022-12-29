package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Review;
import io.pakland.mdas.githubstats.domain.ReviewAggregation;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AggregateReviews {

    ReviewAggregation reviewAggregation = new ReviewAggregation();

    public AggregateReviews() {
    }

    public Map<Team, Map<User, ReviewAggregation>> execute(List<Review> reviews) {
        // {
        //   "userA": {
        //      "teamA": [reviewsFromUserAInTeamA],
        //      "teamB": [reviewsFromUserAInTeamB]
        //   },
        //   "userB": { "teamA": [reviewsFromUserBInTeamA] },
        //   "userC": { "teamB": [reviewsFromUserCInTeamB] } ...
        // }
        Map<Team, Map<User, List<Review>>> groupedReviews = reviews.stream().collect(
            Collectors.groupingBy(review -> review.getUser().getTeam(),
                Collectors.groupingBy(Review::getUser)));

        // {
        //   "userA": {
        //      "teamA": reviewAggregationUserATeamA
        //      "teamB": reviewAggregationUserATeamB
        //   },
        //   "userB": { "teamA": reviewAggregationUserBTeamA },
        //   "userC": { "teamB": reviewAggregationUserCTeamB } ...
        // }
        Map<Team, Map<User, ReviewAggregation>> aggReviews = new HashMap<>();
        groupedReviews.forEach((team, userReviewMap) -> {
            Map<User, ReviewAggregation> aggReviewsByUser = new HashMap<>();
            userReviewMap.forEach((user, reviewList) ->
                aggReviewsByUser.put(user, reviewAggregation.aggregate(reviewList))
            );
            aggReviews.put(team, aggReviewsByUser);
        });

        return aggReviews;
    }

}
