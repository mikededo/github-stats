package io.pakland.mdas.githubstats.domain.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ReviewAggregationTest extends CSVTest {

    @Test
    public void emptyAggregationToCSV_shouldGiveValidCSV() {
        UserReviewAggregation userReviewAggregation = new UserReviewAggregation();
        assertTrue(isValidCSV(userReviewAggregation.toCSV()));
    }

    @Test
    public void nonEmptyAggregationToCSV_shouldGiveValidCSV() {
        List<Review> reviews = List.of(mock(Review.class), mock(Review.class), mock(Review.class));
        UserReviewAggregation userReviewAggregation = new UserReviewAggregation().aggregate(reviews);
        assertTrue(isValidCSV(userReviewAggregation.toCSV()));
    }

}
