package io.pakland.mdas.githubstats.application.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.domain.entity.Comment;
import io.pakland.mdas.githubstats.domain.entity.UserReview;
import io.pakland.mdas.githubstats.domain.entity.UserReviewAggregation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AggregateUserReviewsTest {

    @Test
    public void aggregatingReviews_shouldGiveValidCommentLengthAvg() {
        int sum = 0;
        List<UserReview> reviews = new ArrayList<>();
        Random random = new Random();

        // Generate 10 User reviews, each with random comments
        for (int i = 0; i < 10; i++) {
            List<Comment> comments = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Comment comment = Mockito.mock(Comment.class);
                int randomLength = random.nextInt(10);
                comment.setBody(" ".repeat(randomLength));
                comments.add(comment);
                sum += comment.getLength();  // accumulate comment length to assert at the end
            }
            UserReview userReview = Mockito.mock(UserReview.class);
            userReview.setComments(comments);
            reviews.add(userReview);
        }
        float avg = sum / 10f;

        UserReviewAggregation userReviewAggregation = new UserReviewAggregation();
        float commentLengthAvg = userReviewAggregation.aggregate(reviews).getCommentLengthAvg();
        assertEquals(avg, commentLengthAvg);
    }
}
