package io.pakland.mdas.githubstats.infrastructure.shell.controller;

import io.pakland.mdas.githubstats.FetchUsersFromTeam;
import io.pakland.mdas.githubstats.application.FetchAvailableOrganizations;
import io.pakland.mdas.githubstats.application.FetchRepositoriesFromTeam;
import io.pakland.mdas.githubstats.application.FetchTeamsFromOrganization;
import io.pakland.mdas.githubstats.application.dto.*;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.RepositoryMapper;
import io.pakland.mdas.githubstats.application.mappers.TeamMapper;
import io.pakland.mdas.githubstats.application.mappers.UserMapper;
import io.pakland.mdas.githubstats.domain.*;
import io.pakland.mdas.githubstats.domain.repository.UserExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.*;
import io.pakland.mdas.githubstats.domain.repository.OrganizationExternalRepository;
import io.pakland.mdas.githubstats.domain.repository.RepositoryExternalRepository;
import io.pakland.mdas.githubstats.domain.repository.TeamExternalRepository;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;
import java.util.ArrayList;
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

    public UserOptionController(UserOptionRequest userOptionRequest) {
        this.userOptionRequest = userOptionRequest;
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
            "https://api.github.com", userOptionRequest.getApiKey());
        this.organizationExternalRepository = new OrganizationGitHubRepository(webClientConfiguration);
        this.teamExternalRepository = new TeamGitHubRepository(webClientConfiguration);
        this.userExternalRepository = new UserGitHubRepository(webClientConfiguration);
        this.repositoryExternalRepository = new RepositoryGitHubRepository(webClientConfiguration);
    }

    public void execute() {
        try {
            // Fetch the API key's available organizations.
            List<OrganizationDTO> organizationDTOList = new FetchAvailableOrganizations(this.organizationExternalRepository)
                    .execute();
            // Start building the github-stats relational schema.
            for (OrganizationDTO organizationDTO : organizationDTOList) {
                // Fetch the teams belonging to the available organization DTOs.
                List<TeamDTO> teamDTOList = new FetchTeamsFromOrganization(teamExternalRepository)
                        .execute(organizationDTO.getId());

                for (TeamDTO teamDTO : teamDTOList) {
                    // Fetch the members of each team.
                    List<UserDTO> userDTOList = new FetchUsersFromTeam(userExternalRepository)
                            .execute(organizationDTO.getId(), teamDTO.getId());
                    // Fetch the repositories for each team.
                    List<RepositoryDTO> repositoryDTOList = new FetchRepositoriesFromTeam(repositoryExternalRepository)
                            .execute(organizationDTO.getId(), teamDTO.getId());

                    for (RepositoryDTO repositoryDTO : repositoryDTOList) {
                        //TODO: obtain pull requests
                        List<PullRequest> pullRequests = fetchPullRequestsFromRepository();
                        //TODO: for each pr:
                        //  TODO: if the user of the PR belongs to the team, increment prs_executed.
                        //  TODO: fetch commits from PR
                        //TODO: add
                    }
                    teamDTO.setUsers(userDTOList);
                    teamDTO.setRepositories(repositoryDTOList);
                    teamDTOList.add(teamDTO);
                }
                organizationDTO.setTeams(teamDTOList);
                organizationDTOList.add(organizationDTO);
            }
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PullRequest> fetchPullRequestsFromRepository()
        throws HttpException {
        return null;
    }
}
