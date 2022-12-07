package io.pakland.mdas.githubstats.infrastructure.shell.controller;

import io.pakland.mdas.githubstats.application.FetchAvailableOrganizations;
import io.pakland.mdas.githubstats.application.FetchTeamsFromOrganization;
import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.mappers.TeamMapper;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.OrganizationRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.TeamRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.IOrganizationRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.ITeamRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class UserOptionController {
    private UserOptionRequest userOptionRequest;
    private IOrganizationRESTRepository organizationRESTRepository;
    private ITeamRESTRepository teamRESTRepository;

    Logger logger = LoggerFactory.getLogger(UserOptionController.class);

    public UserOptionController(UserOptionRequest userOptionRequest) {
        this.userOptionRequest = userOptionRequest;
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration("https://api.github.com", userOptionRequest.getApiKey());
        this.organizationRESTRepository = new OrganizationRESTRepository(webClientConfiguration);
        this.teamRESTRepository = new TeamRESTRepository(webClientConfiguration);
    }

    public void execute() {

        FetchAvailableOrganizations fetchAvailableOrganizations = new FetchAvailableOrganizations(this.organizationRESTRepository);
        try {
            List<OrganizationDTO> organizationDTOList = fetchAvailableOrganizations.fetch();
            List<Organization> organizations = new ArrayList<>();
            // TODO: Implement nested loops with RXJava.
            for(OrganizationDTO organizationDTO : organizationDTOList) {
                Organization organization = new Organization();
                 organization.setId(organizationDTO.getId().longValue());
                 organization.setName(organizationDTO.getLogin());

                 List<TeamDTO> teamDTOList = new FetchTeamsFromOrganization(teamRESTRepository).execute(organizationDTO.getLogin());
                 List<Team> teamList = teamDTOList.stream().map(TeamMapper::dtoToEntity).toList();
                 organization.setTeams(teamList);
                 organizations.add(organization);
                logger.info(organizations.toString());
            }
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

}
