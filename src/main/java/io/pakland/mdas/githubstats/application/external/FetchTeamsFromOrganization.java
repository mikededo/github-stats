package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.repository.TeamExternalRepository;
import java.util.List;

public class FetchTeamsFromOrganization {

    private final TeamExternalRepository teamExternalRepository;

    public FetchTeamsFromOrganization(TeamExternalRepository teamExternalRepository) {
        this.teamExternalRepository = teamExternalRepository;
    }

    public List<Team> execute(Organization organization) throws HttpException {
        List<Team> teams = teamExternalRepository.fetchTeamsFromOrganization(organization);
        teams.forEach(team -> team.setOrganization(organization));
        return teams;
    }
}
