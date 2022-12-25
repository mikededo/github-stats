package io.pakland.mdas.githubstats.application.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.domain.entity.Comment;
import io.pakland.mdas.githubstats.domain.entity.Review;
import io.pakland.mdas.githubstats.domain.entity.ReviewAggregation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AggregateReviewsTest {

    @Test
    public void aggregatingReviews_shouldGiveValidCommentLengthAvg() {
        int sum = 0;
        List<Review> reviews = new ArrayList<>();
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
            Review review = Mockito.mock(Review.class);
            review.setComments(comments);
            reviews.add(review);
        }
        float avg = sum / 10f;

        ReviewAggregation reviewAggregation = new ReviewAggregation();
        float commentLengthAvg = reviewAggregation.aggregate(reviews).getCommentLengthAvg();
        assertEquals(avg, commentLengthAvg);
    }
}
