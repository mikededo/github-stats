package io.pakland.mdas.githubstats.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class ReviewAggregation {

    private int bodyLength;
    private int internalReviewCount;
    private int externalReviewCount;

    private ReviewAggregation() {
        this.bodyLength = 0;
        this.internalReviewCount = 0;
        this.externalReviewCount = 0;
    }

    public static ReviewAggregation aggregate(List<Review> reviews) {
        ReviewAggregation aggregation = new ReviewAggregation();
        reviews.forEach(review -> {
            if (review.isInternal()) {
                aggregation.internalReviewCount++;
            } else {
                aggregation.externalReviewCount++;
            }

            aggregation.bodyLength += review.bodySize();
        });
        return aggregation;
    }

    public int averageBodyLength() {
        return bodyLength / (internalReviewCount + externalReviewCount);
    }
}
