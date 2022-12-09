package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;

import java.util.List;

public interface RepositoryExternalRepository {
    List<RepositoryDTO> fetchTeamRepositories(Integer organizationId, Integer teamId) throws HttpException;
}
