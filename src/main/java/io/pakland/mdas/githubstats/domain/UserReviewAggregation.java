package io.pakland.mdas.githubstats.domain;

import java.util.List;

public class UserReviewAggregation {

    private int commentLengthSum;

    public static UserReviewAggregation aggregate(List<UserReview> userReviews) {
        UserReviewAggregation userReviewAggregation = new UserReviewAggregation();
        userReviewAggregation.commentLengthSum = userReviews.stream().mapToInt(UserReview::sumCommentLength).sum();
        return userReviewAggregation;
    }

    public int getCommentLengthSum() {
        return commentLengthSum;
    }

}
