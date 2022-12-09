package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.repository.RepositoryExternalRepository;

import java.util.List;

public class FetchRepositoriesFromTeam {

    private final RepositoryExternalRepository repositoryExternalRepository;

    public FetchRepositoriesFromTeam(RepositoryExternalRepository repositoryExternalRepository) {
        this.repositoryExternalRepository = repositoryExternalRepository;
    }

    public List<RepositoryDTO> execute(Integer organizationId, Integer teamId) throws HttpException {
        return this.repositoryExternalRepository.fetchTeamRepositories(organizationId, teamId);
    }
}
