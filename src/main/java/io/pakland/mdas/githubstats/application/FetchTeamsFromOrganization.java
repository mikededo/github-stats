package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.repository.TeamExternalRepository;

import java.util.List;

public class FetchTeamsFromOrganization {
    private final TeamExternalRepository teamExternalRepository;

    public FetchTeamsFromOrganization(TeamExternalRepository teamExternalRepository) {
        this.teamExternalRepository = teamExternalRepository;
    }

    public List<TeamDTO> execute(Integer organizationId) throws HttpException {
        return teamExternalRepository.fetchTeamsFromOrganization(organizationId);
    }
}
