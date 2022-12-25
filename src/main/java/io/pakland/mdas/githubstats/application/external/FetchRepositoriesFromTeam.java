package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.repository.RepositoryExternalRepository;
import java.util.List;

public class FetchRepositoriesFromTeam {

    private final RepositoryExternalRepository repositoryExternalRepository;

    public FetchRepositoriesFromTeam(RepositoryExternalRepository repositoryExternalRepository) {
        this.repositoryExternalRepository = repositoryExternalRepository;
    }

    public List<Repository> execute(Team team) throws HttpException {
        List<Repository> repositories = this.repositoryExternalRepository.fetchTeamRepositories(
            team);
        team.addRepositories(repositories);
        return repositories;
    }
}
