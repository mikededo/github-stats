package io.pakland.mdas.githubstats.domain;

import java.util.List;

public class UserReviewAggregation {

    private final int commentLengthSum;

    private UserReviewAggregation(int commentLengthSum) {
        this.commentLengthSum = commentLengthSum;
    }

    public static UserReviewAggregation aggregate(List<UserReview> userReviews) {
        int commentLengthSum = userReviews.stream().mapToInt(UserReview::sumCommentLength).sum();
        return new UserReviewAggregation(commentLengthSum);
    }

    public int getCommentLengthSum() {
        return commentLengthSum;
    }

}
