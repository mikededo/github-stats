package io.pakland.mdas.githubstats.domain;

import java.util.List;

public class UserReviewAggregation {

    private int commentLengthSum = 0;
    private int commentCount = 0;

    public UserReviewAggregation aggregate(List<UserReview> userReviews) {
        commentCount = userReviews.size();
        commentLengthSum = userReviews.stream().mapToInt(UserReview::sumCommentLength).sum();
        return this;
    }

    public float getCommentLengthAvg() {
        if(commentCount == 0) return 0;
        return (float) commentLengthSum / commentCount;
    }

}
