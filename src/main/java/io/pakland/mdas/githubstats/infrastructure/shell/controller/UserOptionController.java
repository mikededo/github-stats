package io.pakland.mdas.githubstats.infrastructure.shell.controller;

import io.pakland.mdas.githubstats.application.FetchAvailableOrganizations;
import io.pakland.mdas.githubstats.application.FetchTeamsFromOrganization;
import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.RepositoryMapper;
import io.pakland.mdas.githubstats.application.mappers.TeamMapper;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.Repository;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.OrganizationRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.RepositoryRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.TeamRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.IOrganizationRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.IRepositoryRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.ITeamRESTRepository;
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
    private IOrganizationRESTRepository organizationRESTRepository;
    private ITeamRESTRepository teamRESTRepository;
    private IRepositoryRESTRepository repositoryRestRepository;

    public UserOptionController(UserOptionRequest userOptionRequest) {
        this.userOptionRequest = userOptionRequest;
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
            "https://api.github.com", userOptionRequest.getApiKey());
        this.organizationRESTRepository = new OrganizationRESTRepository(webClientConfiguration);
        this.teamRESTRepository = new TeamRESTRepository(webClientConfiguration);
        this.repositoryRestRepository = new RepositoryRESTRepository(webClientConfiguration);
    }

    public void execute() {

        FetchAvailableOrganizations fetchAvailableOrganizations = new FetchAvailableOrganizations(
            this.organizationRESTRepository);
        try {
            // dto coming from the backend
            List<OrganizationDTO> organizationDTOList = fetchAvailableOrganizations.fetch();
            // entities that we will store in the database
            List<Organization> organizations = new ArrayList<>();
            for (OrganizationDTO organizationDTO : organizationDTOList) {
                // fetch the organization
                Organization organization = new Organization();
                organization.setId(organizationDTO.getId().longValue());
                organization.setName(organizationDTO.getLogin());

                // with the organization, get the teams and map them to the entities
                List<TeamDTO> teamDTOList = new FetchTeamsFromOrganization(
                    teamRESTRepository).execute(
                    organizationDTO.getLogin());
                List<Team> teamList = teamDTOList.stream().map(TeamMapper::dtoToEntity).toList();
                organization.setTeams(teamList);
                organizations.add(organization);

                // obtain the repositories for each team
                for (Team team : teamList) {
                    List<RepositoryDTO> repositoryDTOS = repositoryRestRepository.fetchTeamRepositories(
                        organizationDTO.getId(), team.getId().intValue());
                    List<Repository> repositories = repositoryDTOS.stream()
                        .map(RepositoryMapper::dtoToEntity).toList();
                }
            }
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

}
