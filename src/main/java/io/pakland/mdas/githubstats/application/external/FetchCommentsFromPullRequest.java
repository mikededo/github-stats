package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Comment;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.repository.CommentExternalRepository;

import java.util.ArrayList;
import java.util.List;

public class FetchCommentsFromPullRequest {
    private final CommentExternalRepository commentExternalRepository;

    public FetchCommentsFromPullRequest(CommentExternalRepository commentExternalRepository) {
        this.commentExternalRepository = commentExternalRepository;
    }

    public List<Comment> execute(PullRequest pullRequest) throws HttpException {
        Integer page = 1;
        List<Comment> commentList = new ArrayList<>();
        Integer responseResults;
        Repository repository = pullRequest.getRepository();
        do {
            CommentExternalRepository.FetchCommentsFromPullRequestRequest request = CommentExternalRepository.FetchCommentsFromPullRequestRequest.builder()
                    .repositoryOwner(repository.getOwnerLogin())
                    .repositoryName(repository.getName())
                    .pullRequestNumber(pullRequest.getNumber())
                    .page(page)
                    .perPage(100)
                    .build();
            List<Comment> apiResults = this.commentExternalRepository.fetchCommentsFromPullRequest(request);
            commentList.addAll(apiResults);
            responseResults = apiResults.size();
            page++;
        } while (responseResults > 0);

        return commentList;
    }
}
