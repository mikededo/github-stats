package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Comment;
import io.pakland.mdas.githubstats.domain.PullRequest;

import java.util.List;

public interface CommentExternalRepository {

    public List<Comment> fetchCommentsFromPullRequestByPage(PullRequest pullRequest, Integer page) throws HttpException;
}