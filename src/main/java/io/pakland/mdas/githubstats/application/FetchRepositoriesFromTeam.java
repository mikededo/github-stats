package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Repository;
import io.pakland.mdas.githubstats.domain.repository.RepositoryExternalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchRepositoriesFromTeam {

    private final RepositoryExternalRepository repositoryExternalRepository;

    public FetchRepositoriesFromTeam(RepositoryExternalRepository repositoryExternalRepository) {
        this.repositoryExternalRepository = repositoryExternalRepository;
    }

    public List<Repository> execute(String organizationLogin, String teamSlug) throws HttpException {
        return this.repositoryExternalRepository.fetchTeamRepositories(organizationLogin, teamSlug);
    }
}
