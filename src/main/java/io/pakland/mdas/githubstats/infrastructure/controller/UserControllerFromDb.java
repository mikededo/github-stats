package io.pakland.mdas.githubstats.infrastructure.controller;

import io.pakland.mdas.githubstats.application.FetchAvailableOrganizations;
import io.pakland.mdas.githubstats.application.GetUserByLogin;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.exceptions.UserLoginNotFound;
import io.pakland.mdas.githubstats.domain.*;
import io.pakland.mdas.githubstats.domain.repository.OrganizationExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.OrganizationGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;

import java.util.List;

public class UserControllerFromDb {

    private UserOptionRequest userOptionRequest;

    private GetUserByLogin getUserByLogin;

    private OrganizationExternalRepository organizationExternalRepository;

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
        }
        catch (HttpException e) { throw new RuntimeException(e); }

        User user = getUserByLogin.execute(userOptionRequest.getUserName());

        // TODO
        // List<Commit> userCommits = new ArrayList()<>;
        // List<PullRequest> userPullRequests = new ArrayList()<>;

        orgs.forEach(org -> {
            org.getTeams().forEach(team -> {
                team.getRepositories().forEach(repo -> {
                    repo.getPullRequests().forEach(pull -> {

                        // TODO FIX: tell don't ask, remove getters to maintain encapsulation
                        // PRs merged
                        if (pull.getState().equals(PullRequestState.CLOSED) && pull.getUser().equals(user)) {
                            // userPullRequests.add(pull);
                        }

                        // TODO FIX: tell don't ask, remove getters to maintain encapsulation
                        List<Commit> commits = pull.getCommits().stream().filter(x -> x.getUser().equals(user)).toList();
                        // userCommits.addAll(commits);

                        int additions = commits.stream().mapToInt(Commit::getAdditions).sum();
                        int deletions = commits.stream().mapToInt(Commit::getDeletions).sum();
                        // aggregate additions, deletions

                        List<UserReview> userReview =pull.getUserReviews().stream().filter(x -> x.getUser().equals(user)).toList();

                        // TODO FIX: tell don't ask, remove getters to maintain encapsulation
                        long reviewInsideTeam = userReview.stream().filter(x -> x.getPullRequest().getRepository().getTeam().equals(user.getTeam())).count();
                        long reviewOutsideTeam = userReview.size() - reviewInsideTeam;
                        // aggregate reviewInsideTeam, reviewOutsideTeam

                    });
                });
            });
        });

        // TODO: aggregate_orchestrator(AggregateCommits, AggregateUserReviews, AggregatePullRequests)

    }
}