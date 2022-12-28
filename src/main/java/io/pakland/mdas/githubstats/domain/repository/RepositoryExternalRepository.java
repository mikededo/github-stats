package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Repository;
import io.pakland.mdas.githubstats.domain.entity.Team;

import java.util.List;

public interface RepositoryExternalRepository {
    List<Repository> fetchRepositoriesFromTeam(Team team) throws HttpException;
}
