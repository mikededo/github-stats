package io.pakland.mdas.githubstats.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class UserReviewAggregation implements CSVExportable {

    private int internalCommentLengthSum = 0;
    private int externalCommentLengthSum = 0;
    private int internalCommentCount = 0;
    private int externalCommentCount = 0;

    public UserReviewAggregation aggregate(List<UserReview> userReviews) {
        List<UserReview> internalReviews = new ArrayList<>();
        List<UserReview> externalReviews = new ArrayList<>();

        userReviews.forEach(userReview -> {
            if (userReview.isInternal()) {
                internalReviews.add(userReview);
            } else {
                externalReviews.add(userReview);
            }
        });

        internalCommentCount = internalReviews.size();
        externalCommentCount = externalReviews.size();
        internalCommentLengthSum = internalReviews.stream()
            .mapToInt(UserReview::sumCommentLength).sum();
        externalCommentLengthSum = externalReviews.stream()
            .mapToInt(UserReview::sumCommentLength).sum();

        return this;
    }

    public float getInternalCommentLengthAvg() {
        if (internalCommentCount == 0) return 0;
        return (float) internalCommentLengthSum / internalCommentCount;
    }

    public float getExternalCommentLengthAvg() {
        if (externalCommentCount == 0) return 0;
        return (float) externalCommentLengthSum / externalCommentCount;
    }

    public float getTotalCommentLengthAvg() {
        if (internalCommentCount + externalCommentCount == 0) return 0;
        return (float)
                (internalCommentLengthSum + externalCommentLengthSum) /
                (internalCommentCount + externalCommentCount);
    }

    public int getInternalCommentLengthSum() {
        return internalCommentLengthSum;
    }

    public int getExternalCommentLengthSum() {
        return externalCommentLengthSum;
    }

    public int getInternalCommentCount() {
        return internalCommentCount;
    }

    public int getExternalCommentCount() {
        return externalCommentCount;
    }

    @Override
    public String toCSV() {
        String sep = ",";
        String lineSep = "\n";

        List<String> metrics = List.of("internalCommentLengthAvg", "externalCommentLengthAvg");
        List<Object> data = List.of(getInternalCommentLengthAvg(), getExternalCommentLengthAvg());

        String header = String.join(sep, metrics);
        String body = String.join(sep, data.stream().map(Object::toString).toList());

        return header + lineSep + body;
    }
}
