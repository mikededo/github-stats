package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.repository.TeamExternalRepository;

import java.util.List;

public class FetchTeamsFromOrganization {
    private final TeamExternalRepository teamExternalRepository;

    public FetchTeamsFromOrganization(TeamExternalRepository teamExternalRepository) {
        this.teamExternalRepository = teamExternalRepository;
    }

    public List<Team> execute(String organizationName) throws HttpException {
        return teamExternalRepository.fetchTeamsFromOrganization(organizationName);
    }
}
