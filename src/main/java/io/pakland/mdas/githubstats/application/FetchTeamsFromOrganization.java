package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.domain.repository.TeamExternalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchTeamsFromOrganization {
    private final TeamExternalRepository teamExternalRepository;

    public FetchTeamsFromOrganization(TeamExternalRepository teamExternalRepository) {
        this.teamExternalRepository = teamExternalRepository;
    }

    public List<Team> execute(String organizationName) throws HttpException {
        return teamExternalRepository.fetchTeamsFromOrganization(organizationName);
    }
}
