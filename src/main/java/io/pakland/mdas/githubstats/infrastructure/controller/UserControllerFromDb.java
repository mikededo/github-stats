package io.pakland.mdas.githubstats.infrastructure.controller;

import io.pakland.mdas.githubstats.application.AggregateCommits;
import io.pakland.mdas.githubstats.application.FetchAvailableOrganizations;
import io.pakland.mdas.githubstats.application.GetUserByLogin;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.exceptions.UserLoginNotFound;
import io.pakland.mdas.githubstats.domain.*;
import io.pakland.mdas.githubstats.domain.repository.OrganizationExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.OrganizationGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;

import java.util.ArrayList;
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

        List<Commit> userCommits = new ArrayList<>();
        List<PullRequest> userPullRequests = new ArrayList<>();

        orgs.forEach(org -> {
            org.getTeams().forEach(team -> {
                team.getRepositories().forEach(repo -> {
                    repo.getPullRequests().forEach(pull -> {

                        // PRs merged
                        if (pull.isClosed() && pull.isCreatedByUser(user)) {
                            userPullRequests.add(pull);
                        }

                        List<Commit> commits = pull.getCommitsByUser(user);
                        userCommits.addAll(commits);

                        CommitAggregation commitAggregation = new AggregateCommits().execute(commits);
                        int linesAdded = commitAggregation.getLinesAdded();
                        int linesRemoved = commitAggregation.getLinesRemoved();
                        // aggregate additions, deletions

                        List<UserReview> userReviews = pull.getReviewsFromUser(user);

                        long numReviewsInsideTeam = userReviews
                                .stream()
                                .filter(review -> review.isReviewFromTeam(team))
                                .count();
                        long numReviewsOutsideTeam = userReviews.size() - numReviewsInsideTeam;
                        // aggregate numReviewsInsideTeam, numReviewsOutsideTeam

                    });
                });
            });
        });

        // TODO: aggregate_orchestrator(AggregateCommits, AggregateUserReviews, AggregatePullRequests)

    }
}