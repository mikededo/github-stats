package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Comment;
import io.pakland.mdas.githubstats.domain.DateRange;
import io.pakland.mdas.githubstats.domain.PullRequest;
import io.pakland.mdas.githubstats.domain.repository.CommentExternalRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FetchCommentsFromPullRequest {

    private final CommentExternalRepository commentExternalRepository;

    public FetchCommentsFromPullRequest(CommentExternalRepository commentExternalRepository) {
        this.commentExternalRepository = commentExternalRepository;
    }

    public List<Comment> execute(PullRequest pullRequest, DateRange range) throws HttpException {
        int page = 1, responseResults = 0;
        List<Comment> commentList = new ArrayList<>();

        do {
            List<Comment> apiResults = this.commentExternalRepository
                .fetchCommentsFromPullRequestByPage(pullRequest, page);

            if (apiResults.size() == 0) {
                break;
            }

            Instant first = apiResults.get(0).getCreatedAt().toInstant(),
                last = apiResults.get(apiResults.size() - 1).getCreatedAt().toInstant();

            // If first is before the start of the range, then we should not keep fetching the
            // data since all will be outside the range
            if (range.isPreviousToRange(first)) {
                break;
            }

            // If the last one is after the date range, we still need to fetch to check if there
            // are older results that are in range
            if (range.isFollowingToRange(last)) {
                responseResults = apiResults.size();
                page++;
                continue;
            }

            if (range.isBetweenRange(first) && range.isBetweenRange(last)) {
                commentList.addAll(apiResults);
            } else {
                // However, if first one is after the range but the last isn't, we have to filter the
                // commits that are within the range.
                // We have to do the same thing if the last is not in range but the first is
                // Also if the first is after but the last is previous, there may be commits in between
                // that are within the range
                commentList.addAll(apiResults.parallelStream()
                    .filter(commit -> range.isBetweenRange(commit.getCreatedAt().toInstant()))
                    .toList());
            }

            responseResults = apiResults.size();
            page++;
        } while (responseResults > 0);
        commentList.parallelStream().forEach(comment -> comment.setPullRequest(pullRequest));

        return commentList;
    }
}
