package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.repository.TeamExternalRepository;

import java.util.List;

public class FetchTeamsFromOrganization {
    private final TeamExternalRepository teamRESTRepository;

    public FetchTeamsFromOrganization(TeamExternalRepository teamRESTRepository) {
        this.teamRESTRepository = teamRESTRepository;
    }

    public List<TeamDTO> execute(Integer organizationId) throws HttpException {
        return teamRESTRepository.fetchTeamsFromOrganization(organizationId);
    }
}
