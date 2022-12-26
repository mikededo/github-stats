package io.pakland.mdas.githubstats.application.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.domain.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class AggregateReviewsTest {

    @Test
    public void aggregatingReviews_shouldGiveValidCommentLengthAvg() {
        int sum = 0;
        List<Review> reviews = new ArrayList<>();
        Random random = new Random();

        // Generate 10 User reviews, each with random comments
        for (int i = 0; i < 10; i++) {
            List<Comment> comments = new ArrayList<>();

            Team team = new Team();
            User user = new User();
            Repository repo = new Repository();
            PullRequest pr = new PullRequest();
            Review review = new Review();
            repo.setTeam(team);
            user.setTeam(team);
            pr.setRepository(repo);
            review.setPullRequest(pr);
            review.setUser(user);

            for (int j = 0; j < 10; j++) {
                Comment comment = new Comment();
                comment.setReview(review);
                int randomLength = random.nextInt(10);
                comment.setBody(" ".repeat(randomLength));
                comments.add(comment);
                sum += comment.getLength();  // accumulate comment length to assert at the end
            }
            review.setComments(comments);
            reviews.add(review);
        }
        float avg = sum / 10f;

        UserReviewAggregation userReviewAggregation = new UserReviewAggregation().aggregate(reviews);
        float totalCommentLengthAvg = userReviewAggregation.getTotalCommentLengthAvg();

        assertEquals(avg, totalCommentLengthAvg);
    }
}
