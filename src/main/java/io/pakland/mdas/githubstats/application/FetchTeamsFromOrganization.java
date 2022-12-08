package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.TeamExternalRepository;

import java.util.List;

public class FetchTeamsFromOrganization {
    private TeamExternalRepository teamRESTRepository;

    public FetchTeamsFromOrganization(TeamExternalRepository teamRESTRepository) {
        this.teamRESTRepository = teamRESTRepository;
    }

    public List<TeamDTO> execute(String organizationName) throws HttpException {
        return teamRESTRepository.fetchTeamsFromOrganization(organizationName);
    }
}
