package io.pakland.mdas.githubstats.infrastructure.rest.repository.ports;

import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;

import java.util.List;

public interface RepositoryExternalRepository {
    List<RepositoryDTO> fetchTeamRepositories(Integer orgId, Integer teamId) throws HttpException;
}
