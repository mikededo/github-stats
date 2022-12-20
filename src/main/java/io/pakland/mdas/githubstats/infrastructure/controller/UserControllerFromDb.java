package io.pakland.mdas.githubstats.infrastructure.controller;

import io.pakland.mdas.githubstats.application.external.FetchAvailableOrganizations;
import io.pakland.mdas.githubstats.application.internal.AggregateCommits;
import io.pakland.mdas.githubstats.application.internal.AggregatePullRequests;
import io.pakland.mdas.githubstats.application.internal.AggregateUserReviews;
import io.pakland.mdas.githubstats.application.internal.GetUserByLogin;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.exceptions.UserLoginNotFound;
import io.pakland.mdas.githubstats.domain.entity.*;
import io.pakland.mdas.githubstats.domain.repository.OrganizationExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.OrganizationGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;

import java.util.List;

public class UserControllerFromDb {

    private final UserOptionRequest userOptionRequest;

    private final GetUserByLogin getUserByLogin;

    private final OrganizationExternalRepository organizationExternalRepository;

    public UserControllerFromDb(UserOptionRequest userOptionRequest, GetUserByLogin getUserByLogin) {
        this.userOptionRequest = userOptionRequest;
        this.getUserByLogin = getUserByLogin;
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
                "https://api.github.com", userOptionRequest.getApiKey());
        this.organizationExternalRepository = new OrganizationGitHubRepository(
                webClientConfiguration);
    }

    public void execute() throws UserLoginNotFound {
        List<Organization> orgs;

        try {
            orgs = new FetchAvailableOrganizations(this.organizationExternalRepository).execute();
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }

        User user = getUserByLogin.execute(userOptionRequest.getUserName());

        List<PullRequest> pullRequests = getPullRequestsByUser(orgs, user);
        List<Commit> commits = getCommitsByUser(pullRequests, user);
        List<UserReview> userReviews = getUserReviewsByUser(pullRequests, user);

        PullRequestAggregation pullRequestAggregation = new AggregatePullRequests().execute(pullRequests);
        CommitAggregation commitAggregation = new AggregateCommits().execute(commits);
        UserReviewAggregation userReviewAggregation = new AggregateUserReviews().execute(userReviews);

        String pullRequestCSV = pullRequestAggregation.toCSV();
        String commitCSV = commitAggregation.toCSV();
        String userReviewCSV = userReviewAggregation.toCSV();

        // TODO: cache in Redis and/or print CSVs to file
    }

    private List<PullRequest> getPullRequestsByUser(List<Organization> orgs, User user) {
        return orgs.stream()
            .flatMap(org -> org.getTeams().stream())
            .flatMap(team -> team.getRepositories().stream())
            .flatMap(repo -> repo.getPullRequests().stream())
            .filter(pull -> pull.isClosed() && pull.isCreatedByUser(user))
            .toList();
    }

    private List<UserReview> getUserReviewsByUser(List<PullRequest> pullRequests, User user) {
        return pullRequests.stream()
            .flatMap(pull -> pull.getReviewsFromUser(user).stream())
            .toList();
    }

    private List<Commit> getCommitsByUser(List<PullRequest> pullRequests, User user) {
        return pullRequests.stream()
            .flatMap(pull -> pull.getCommitsByUser(user).stream())
            .toList();
    }

}