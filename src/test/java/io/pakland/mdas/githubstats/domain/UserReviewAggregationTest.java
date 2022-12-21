package io.pakland.mdas.githubstats.domain;

import io.pakland.mdas.githubstats.domain.entity.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class UserReviewAggregationTest extends CSVTest {

    @Test
    public void emptyAggregationToCSV_shouldGiveValidCSV() {
        UserReviewAggregation userReviewAggregation = new UserReviewAggregation();
        assertTrue(isValidCSV(userReviewAggregation.toCSV()));
    }

    @Test
    public void nonEmptyAggregationToCSV_shouldGiveValidCSV() {
        List<UserReview> userReviews = List.of(mock(UserReview.class), mock(UserReview.class), mock(UserReview.class));
        UserReviewAggregation userReviewAggregation = new UserReviewAggregation().aggregate(userReviews);
        assertTrue(isValidCSV(userReviewAggregation.toCSV()));
    }

}
