package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.domain.repository.TeamRepository;
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
