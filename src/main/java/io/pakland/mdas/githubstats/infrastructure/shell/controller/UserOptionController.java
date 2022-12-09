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
            // dto coming from the backend
            List<OrganizationDTO> organizationDTOList = new FetchAvailableOrganizations(
                this.organizationExternalRepository).execute();
            // entities that we will store in the database
            List<Organization> userAvaliableOrganizationList = new ArrayList<>();
            for (OrganizationDTO organizationDTO : organizationDTOList) {
                // fetch the organization
                Organization organization = new Organization();
                organization.setId(organizationDTO.getId());
                organization.setName(organizationDTO.getLogin());

                // with the organization, get the teams and map them to the entities
                List<Team> teams = fetchTeamsFromOrganization(organization.getId());
                for (Team team : teams) {
                    // obtain the members of each team
                    List<User> users = fetchUsersFromTeam(organization.getId(),
                        team.getId());
                    // also obtain the repositories for each team
                    List<Repository> repositories = fetchRepositoriesFromTeam(
                        organization.getId(), team.getId());
                    for (Repository repository : repositories) {
                        //TODO: obtain pull requests
                        List<PullRequest> pullRequests = fetchPullRequestsFromRepository();
                        //TODO: for each pr:
                        //  TODO: if the user of the PR belongs to the team, increment prs_executed.
                        //  TODO: fetch commits from PR
                        //TODO: add
                    }
                    team.setUsers(users);
                    team.setRepositories(repositories);
                }
                userAvaliableOrganizationList.add(organization);
            }
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Team> fetchTeamsFromOrganization(Integer organizationId) throws HttpException {
        List<TeamDTO> teamDTOList = new FetchTeamsFromOrganization(
            teamExternalRepository
        ).execute(organizationId);
        // we only need the teams of the current user
        return teamDTOList.stream().map(TeamMapper::dtoToEntity).toList();
    }

    private List<User> fetchUsersFromTeam(Integer organizationId, Integer teamId) throws HttpException {
        List<UserDTO> users = new FetchUsersFromTeam(userExternalRepository).execute(organizationId, teamId);
        return users.stream().map(UserMapper::dtoToEntity).toList();
    }

    private List<Repository> fetchRepositoriesFromTeam(Integer organizationId, Integer teamId)
        throws HttpException {
        List<RepositoryDTO> repositoryDTOS = new FetchRepositoriesFromTeam(repositoryExternalRepository).execute(organizationId, teamId);
        return repositoryDTOS.stream().map(RepositoryMapper::dtoToEntity).toList();
    }

    private List<PullRequest> fetchPullRequestsFromRepository()
        throws HttpException {
//        List<PullRequestDTO> pullRequestDTOS =
        return null;
    }
}
