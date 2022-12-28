package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.Review;
import io.pakland.mdas.githubstats.domain.entity.ReviewAggregation;
import io.pakland.mdas.githubstats.domain.entity.Team;
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

    public Map<User, Map<Team, ReviewAggregation>> execute(List<Review> reviews) {
        // {
        //   "userA": {
        //      "teamA": [reviewsFromUserAInTeamA],
        //      "teamB": [reviewsFromUserAInTeamB]
        //   },
        //   "userB": { "teamA": [reviewsFromUserBInTeamA] },
        //   "userC": { "teamB": [reviewsFromUserCInTeamB] } ...
        // }
        Map<User, Map<Team, List<Review>>> groupedReviews = reviews.stream().collect(
            Collectors.groupingBy(Review::getUser,
            Collectors.groupingBy(review ->
                review.getPullRequest().getRepository().getTeam()
            )));

        // {
        //   "userA": {
        //      "teamA": reviewAggregationUserATeamA
        //      "teamB": reviewAggregationUserATeamB
        //   },
        //   "userB": { "teamA": reviewAggregationUserBTeamA },
        //   "userC": { "teamB": reviewAggregationUserCTeamB } ...
        // }
        Map<User, Map<Team, ReviewAggregation>> aggReviews = new HashMap<>();
        groupedReviews.keySet().forEach(user -> {
            Map<Team, ReviewAggregation> aggReviewsByUser = new HashMap<>();
            groupedReviews.get(user).keySet().forEach(team -> {
                List<Review> reviewsByUserByTeam = groupedReviews.get(user).get(team);
                aggReviewsByUser.put(team, reviewAggregation.aggregate(reviewsByUserByTeam));
            });
            aggReviews.put(user, aggReviewsByUser);
        });

        return aggReviews;
    }

}
