package io.pakland.mdas.githubstats.infrastructure.shell.controller;

import io.pakland.mdas.githubstats.FetchUsersFromTeam;
import io.pakland.mdas.githubstats.application.FetchAvailableOrganizations;
import io.pakland.mdas.githubstats.application.FetchPullRequestsFromRepository;
import io.pakland.mdas.githubstats.application.FetchRepositoriesFromTeam;
import io.pakland.mdas.githubstats.application.FetchTeamsFromOrganization;
import io.pakland.mdas.githubstats.application.dto.PullRequestDTO;
import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.domain.repository.*;
import io.pakland.mdas.githubstats.infrastructure.github.repository.*;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public UserOptionController(UserOptionRequest userOptionRequest) {
        this.userOptionRequest = userOptionRequest;
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
            "https://api.github.com", userOptionRequest.getApiKey());
        this.organizationExternalRepository = new OrganizationGitHubRepository(webClientConfiguration);
        this.teamExternalRepository = new TeamGitHubRepository(webClientConfiguration);
        this.userExternalRepository = new UserGitHubRepository(webClientConfiguration);
        this.repositoryExternalRepository = new RepositoryGitHubRepository(webClientConfiguration);
        this.pullRequestExternalRepository = new PullRequestGitHubRepository(webClientConfiguration);
    }

    public void execute() {
        try {
            // TODO: If the execution succeeds, we should make an entry to the historic_queries table.
            // Fetch the API key's available organizations.
            List<Organization> organizationList = new FetchAvailableOrganizations(this.organizationExternalRepository)
                    .execute();
            // Start building the github-stats relational schema.
            for (Organization organization : organizationList) {
                // Fetch the teams belonging to the available organization DTOs.
                List<Team> teamList = new FetchTeamsFromOrganization(teamExternalRepository)
                        .execute(organization.getId());

                for (Team team : teamList) {
                    // Fetch the members of each team.
                    List<UserDTO> userDTOList = new FetchUsersFromTeam(userExternalRepository)
                            .execute(organization.getId(), team.getId());
                    // Fetch the repositories for each team.
                    List<RepositoryDTO> repositoryDTOList = new FetchRepositoriesFromTeam(repositoryExternalRepository)
                            .execute(organization.getId(), team.getId());

                    for (RepositoryDTO repositoryDTO : repositoryDTOList) {
                        // Fetch pull requests from each team.
                        List<PullRequestDTO> pullRequestDTOList = new FetchPullRequestsFromRepository(pullRequestExternalRepository)
                                .execute(repositoryDTO.getRepositoryOwnerId(), repositoryDTO.getId());
                        for (PullRequestDTO pullRequestDTO : pullRequestDTOList) {
                            /*
                                TODO: if the user of the PR belongs to the team, increment the prs executed inside the team,
                                TODO: else increment the prs executed outside the team.
                            */
                            //  TODO: fetch commits from each PR.
                        }

                        repositoryDTO.setPullRequests(pullRequestDTOList);
                    }

                    //TODO: Set user entity list to team.
//                    team.setUsers(userDTOList);
                    //TODO: Set repositoty entity list to team.
//                    team.setRepositories(repositoryDTOList);
                }

                // TODO: Set teams entity list to organization.
                organization.setTeams(teamList);
            }
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }
}
