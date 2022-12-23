package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Comment;

import java.util.List;

public interface CommentExternalRepository {
    public List<Comment> fetchCommentsFromPullRequest(
            String repositoryOwner, String repositoryName, Integer pullRequestNumber, Integer page, Integer perPage) throws HttpException;
}