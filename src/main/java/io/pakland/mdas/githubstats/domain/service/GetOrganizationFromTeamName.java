package io.pakland.mdas.githubstats.domain.service;

import io.pakland.mdas.githubstats.domain.model.Organization;
import io.pakland.mdas.githubstats.domain.model.Team;
import io.pakland.mdas.githubstats.domain.ports.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetOrganizationFromTeamName {
    private final TeamRepository teamRepository;

    public GetOrganizationFromTeamName(TeamRepository teamRepo) {
        this.teamRepository = teamRepo;
    }

    public Organization execute(String teamName) {
        Optional<Team> maybeTeam = teamRepository.findTeamByName(teamName);
        if (maybeTeam.isEmpty()) {
            // TODO: Add exception
            return null;
        }

        return maybeTeam.get().getOrganization();
    }
}
