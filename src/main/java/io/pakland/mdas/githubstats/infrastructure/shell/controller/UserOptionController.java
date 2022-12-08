package io.pakland.mdas.githubstats.infrastructure.shell.controller;

import io.pakland.mdas.githubstats.application.FetchAvailableOrganizations;
import io.pakland.mdas.githubstats.application.FetchTeamsFromOrganization;
import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.RepositoryMapper;
import io.pakland.mdas.githubstats.application.mappers.TeamMapper;
import io.pakland.mdas.githubstats.application.mappers.UserMapper;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.Repository;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.domain.User;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.OrganizationGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.RepositoryGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.TeamGitHubRepository;
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
    private OrganizationExternalRepository organizationRESTRepository;
    private TeamExternalRepository teamRESTRepository;
    private RepositoryExternalRepository repositoryRESTRepository;

    public UserOptionController(UserOptionRequest userOptionRequest) {
        this.userOptionRequest = userOptionRequest;
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
            "https://api.github.com", userOptionRequest.getApiKey());
        this.organizationRESTRepository = new OrganizationGitHubRepository(webClientConfiguration);
        this.teamRESTRepository = new TeamGitHubRepository(webClientConfiguration);
        this.repositoryRESTRepository = new RepositoryGitHubRepository(webClientConfiguration);
    }

    public void execute() {
        try {
            // dto coming from the backend
            List<OrganizationDTO> organizationDTOList = new FetchAvailableOrganizations(
                this.organizationRESTRepository).fetch();
            // entities that we will store in the database
            List<Organization> organizations = new ArrayList<>();
            for (OrganizationDTO organizationDTO : organizationDTOList) {
                // fetch the organization
                Organization organization = new Organization();
                organization.setId(organizationDTO.getId().longValue());
                organization.setName(organizationDTO.getLogin());

                // with the organization, get the teams and map them to the entities
                List<Team> teams = fetchTeamsFromOrganization(organizationDTO.getLogin());
                organization.setTeams(teams);
                organizations.add(organization);

                for (Team team : teams) {
                    // obtain the members of each team
                    List<User> users = fetchUsersFromTeam(organizationDTO.getLogin(),
                        team.getSlug());
                    team.setUsers(users);
                    // also obtain the repositories for each team
                    List<Repository> repositories = fetchRepositoriesFromTeam(
                        organizationDTO.getId(), team.getId().intValue());
                }
            }
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Team> fetchTeamsFromOrganization(String organization) throws HttpException {
        List<TeamDTO> teamDTOList = new FetchTeamsFromOrganization(
            teamRESTRepository
        ).execute(organization);
        // we only need the teams of the current user
        return teamDTOList.stream().map(TeamMapper::dtoToEntity).toList();
    }

    private List<User> fetchUsersFromTeam(String orgLogin, String teamSlug) throws HttpException {
        List<UserDTO> users = teamRESTRepository.fetchMembersOfTeam(orgLogin, teamSlug);
        return users.stream().map(UserMapper::dtoToEntity).toList();
    }

    private List<Repository> fetchRepositoriesFromTeam(Integer orgId, Integer teamId)
        throws HttpException {
        List<RepositoryDTO> repositoryDTOS = repositoryRESTRepository.fetchTeamRepositories(
            orgId, teamId);
        return repositoryDTOS.stream().map(RepositoryMapper::dtoToEntity).toList();
    }
}
