package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaveAllTeams {

    public final TeamRepository teamRepository;

    public SaveAllTeams(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional
    public void execute(List<Team> teams) {
        teamRepository.saveAll(teams);
    }

}
