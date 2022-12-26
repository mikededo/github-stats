package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Comment;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.repository.CommentExternalRepository;

import java.util.ArrayList;
import java.util.List;

public class FetchCommentsFromPullRequest {

    private final CommentExternalRepository commentExternalRepository;

    public FetchCommentsFromPullRequest(CommentExternalRepository commentExternalRepository) {
        this.commentExternalRepository = commentExternalRepository;
    }

    public List<Comment> execute(PullRequest pullRequest) throws HttpException {
        int page = 1, responseResults = 0;
        List<Comment> commentList = new ArrayList<>();
        do {
            List<Comment> apiResults = this.commentExternalRepository
                .fetchCommentsFromPullRequestByPage(pullRequest, page);

            commentList.addAll(apiResults);
            responseResults = apiResults.size();
            page++;
        } while (responseResults > 0);

        return commentList;
    }
}
