package io.pakland.mdas.githubstats.domain;

import java.util.List;

public class CommentAggregation {

    private int bodyLength;
    private int commentCount;

    private CommentAggregation() {
        this.bodyLength = 0;
        this.commentCount = 0;
    }

    public static CommentAggregation aggregate(List<Comment> comments) {
        CommentAggregation aggregation = new CommentAggregation();
        comments.forEach(comment -> {
            aggregation.bodyLength += comment.bodySize();
            aggregation.commentCount++;
        });
        return aggregation;
    }

    public int averageBodyLength() {
        return bodyLength / commentCount;
    }
}
