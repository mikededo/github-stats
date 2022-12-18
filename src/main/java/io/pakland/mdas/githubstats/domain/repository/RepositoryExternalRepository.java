package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Repository;

import java.util.List;

public interface RepositoryExternalRepository {
    List<Repository> fetchTeamRepositories(String organizationLogin, String teamSlug) throws HttpException;
}
