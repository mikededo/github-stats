package io.pakland.mdas.githubstats.application.unnused;

import io.pakland.mdas.githubstats.application.exceptions.TeamNotFound;
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

    public Organization execute(String teamName) throws TeamNotFound {
        Optional<Team> maybeTeam = teamRepository.findTeamBySlug(teamName);
        if (maybeTeam.isEmpty()) {
            throw new TeamNotFound(teamName);
        }

        return maybeTeam.get().getOrganization();
    }
}
