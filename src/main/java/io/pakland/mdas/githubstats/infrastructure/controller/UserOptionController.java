package io.pakland.mdas.githubstats.infrastructure.controller;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.external.*;
import io.pakland.mdas.githubstats.domain.entity.*;
import io.pakland.mdas.githubstats.domain.repository.*;
import io.pakland.mdas.githubstats.infrastructure.github.repository.*;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;
import java.util.List;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserOptionController {

    Logger logger = LoggerFactory.getLogger(UserOptionController.class);
    private OrganizationExternalRepository organizationExternalRepository;
    private TeamExternalRepository teamExternalRepository;
    private UserExternalRepository userExternalRepository;
    private RepositoryExternalRepository repositoryExternalRepository;
    private PullRequestExternalRepository pullRequestExternalRepository;
    private CommitExternalRepository commitExternalRepository;

    public UserOptionController(UserOptionRequest userOptionRequest) {
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
            "https://api.github.com", userOptionRequest.getApiKey());
        this.organizationExternalRepository = new OrganizationGitHubRepository(
            webClientConfiguration);
        this.teamExternalRepository = new TeamGitHubRepository(webClientConfiguration);
        this.userExternalRepository = new UserGitHubRepository(webClientConfiguration);
        this.repositoryExternalRepository = new RepositoryGitHubRepository(webClientConfiguration);
        this.pullRequestExternalRepository = new PullRequestGitHubRepository(
            webClientConfiguration);
        this.commitExternalRepository = new CommitGitHubRepository(webClientConfiguration);
    }

    public void execute() {
        try {
            // TODO: If the execution succeeds, we should make an entry to the historic_queries table.
            // Fetch the API key's available organizations.
            List<Organization> organizationList = new FetchAvailableOrganizations(
                this.organizationExternalRepository)
                .execute();
            organizationList.forEach(this::fetchTeamsFromOrganization);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchTeamsFromOrganization(Organization organization) {
        try {
            List<Team> teamList = new FetchTeamsFromOrganization(teamExternalRepository)
                .execute(organization.getLogin());
            teamList.forEach(team -> {
                this.fetchRepositoriesFromTeam(organization, team);
                this.fetchUsersFromTeam(organization, team);
            });
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }

    }

    private void fetchRepositoriesFromTeam(Organization organization, Team team) {
        organization.addTeam(team);
        try {
            // Fetch the repositories for each team.
            List<Repository> repositoryList = new FetchRepositoriesFromTeam(
                repositoryExternalRepository)
                .execute(organization.getLogin(), team.getSlug());
            // Add the team to the repository
            repositoryList.forEach(repository -> {
                repository.setTeam(team);
                this.fetchPullRequestsFromRepository(team, repository);
            });
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchUsersFromTeam(Organization organization, Team team) {
        try {
            // Fetch the members of each team.
            List<User> userList = new FetchUsersFromTeam(userExternalRepository)
                .execute(organization.getLogin(), team.getSlug());

            team.setUsers(userList);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchPullRequestsFromRepository(Team team, Repository repository) {
        team.addRepository(repository);
        try {
            // Fetch pull requests from each team.
            List<PullRequest> pullRequestList = new FetchPullRequestsFromRepository(
                pullRequestExternalRepository)
                .execute(repository.getOwnerLogin(), repository.getName());

            pullRequestList.forEach(pullRequest -> this.fetchCommitsFromPullRequest(repository, pullRequest));
            repository.setPullRequests(pullRequestList);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }


    private void fetchCommitsFromPullRequest(Repository repository, PullRequest pullRequest) {
        // Add the repository to the pull request
        pullRequest.setRepository(repository);
        /*
        TODO: if the user of the PR belongs to the team, increment the prs executed inside the team
        TODO: else increment the prs executed outside the team
        TODO: Save for later calculate the additions, deletions and commit num. from PR aggregation
        */
        //
        try {
            List<Commit> commitList = new FetchCommitsFromPullRequest(
                commitExternalRepository)
                .execute(repository.getOwnerLogin(), repository.getName(),
                    pullRequest.getNumber());
            for (Commit commit : commitList) {
                // TODO: Fetch PR reviews.

            }
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }
}
