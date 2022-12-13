package io.pakland.mdas.githubstats.infrastructure.shell.controller;

import io.pakland.mdas.githubstats.FetchUsersFromTeam;
import io.pakland.mdas.githubstats.application.*;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.*;
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
    private UserOptionRequest userOptionRequest;
    private OrganizationExternalRepository organizationExternalRepository;
    private TeamExternalRepository teamExternalRepository;
    private UserExternalRepository userExternalRepository;
    private RepositoryExternalRepository repositoryExternalRepository;
    private PullRequestExternalRepository pullRequestExternalRepository;
    private CommitExternalRepository commitExternalRepository;

    public UserOptionController(UserOptionRequest userOptionRequest) {
        this.userOptionRequest = userOptionRequest;
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
                "https://api.github.com", userOptionRequest.getApiKey());
        this.organizationExternalRepository = new OrganizationGitHubRepository(webClientConfiguration);
        this.teamExternalRepository = new TeamGitHubRepository(webClientConfiguration);
        this.userExternalRepository = new UserGitHubRepository(webClientConfiguration);
        this.repositoryExternalRepository = new RepositoryGitHubRepository(webClientConfiguration);
        this.pullRequestExternalRepository = new PullRequestGitHubRepository(webClientConfiguration);
        this.commitExternalRepository = new CommitGitHubRepository(webClientConfiguration);
    }

    public void execute() {
        try {
            // TODO: If the execution succeeds, we should make an entry to the historic_queries table.
            // Fetch the API key's available organizations.
            List<Organization> organizationList = new FetchAvailableOrganizations(
                this.organizationExternalRepository)
                .execute();
            // Start building the github-stats relational schema.
            for (Organization organization : organizationList) {
                // Fetch the teams belonging to the available organization.
                List<Team> teamList = new FetchTeamsFromOrganization(teamExternalRepository)
                    .execute(organization.getLogin());

                for (Team team : teamList) {
                    // Fetch the members of each team.
                    List<User> userList = new FetchUsersFromTeam(userExternalRepository)
                        .execute(organization.getLogin(), team.getSlug());
                    // Fetch the repositories for each team.
                    List<Repository> repositoryList = new FetchRepositoriesFromTeam(
                        repositoryExternalRepository)
                        .execute(organization.getLogin(), team.getSlug());
                    // Add the team to the repository
                    repositoryList.forEach(r -> r.setTeam(team));

                    for (Repository repository : repositoryList) {
                        // Fetch pull requests from each team.
                        List<PullRequest> pullRequestList = new FetchPullRequestsFromRepository(
                            pullRequestExternalRepository)
                            .execute(repository.getOwnerLogin(), repository.getName());

                        for (PullRequest pullRequest : pullRequestList) {
                            // Add the repository to the pull request
                            pullRequest.setRepository(repository);
                            /*
                                TODO: if the user of the PR belongs to the team, increment the prs executed inside the team,
                                TODO: else increment the prs executed outside the team.
                            */
                            // TODO: Save for later calculate the Additions from PR aggregation.
                            // TODO: Save for later calculate the Deletions from PR aggregation.
                            // TODO: Save for later calculate the commit number from PR aggregation.
                            List<Commit> commitList = new FetchCommitsFromPullRequest(commitExternalRepository)
                                    .execute(repository.getOwnerLogin(), repository.getName(), pullRequest.getNumber());
                            logger.info(String.valueOf(commitList.size()));
                            for (Commit commit : commitList) {
                                // TODO: Fetch PR reviews.
                            }
                        }

                        repository.setPullRequests(pullRequestList);
                    }

                    team.setUsers(userList);
                    team.setRepositories(repositoryList);
                }

                organization.setTeams(teamList);
            }
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }
}
