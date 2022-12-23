package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Comment;
import io.pakland.mdas.githubstats.domain.repository.CommentExternalRepository;

import java.util.ArrayList;
import java.util.List;

public class FetchCommentsFromPullRequest {
    private final CommentExternalRepository commentExternalRepository;

    public FetchCommentsFromPullRequest(CommentExternalRepository commentExternalRepository) {
        this.commentExternalRepository = commentExternalRepository;
    }

    public List<Comment> execute(
            String repositoryOwner, String repositoryName, Integer pullRequestNumber)
            throws HttpException {
        int page = 1;
        List<Comment> commentList = new ArrayList<>();
        int responseResults;
        do {
            List<Comment> apiResults = this.commentExternalRepository.fetchCommentsFromPullRequest(
                    repositoryOwner, repositoryName, pullRequestNumber, page, 100
            );
            commentList.addAll(apiResults);
            responseResults = apiResults.size();
            page++;
        } while (responseResults > 0);

        return commentList;
    }
}
