package io.pakland.mdas.githubstats.domain.service;

import io.pakland.mdas.githubstats.domain.model.Repository;
import io.pakland.mdas.githubstats.domain.model.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetRepositoriesByTeam {

    public GetRepositoriesByTeam() {}

    @Transactional
    public List<Repository> execute(Team team) {
        return team.getRepositories();
    }

}
