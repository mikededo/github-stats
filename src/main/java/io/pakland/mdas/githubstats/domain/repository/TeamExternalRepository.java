package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Team;

import java.util.List;

public interface TeamExternalRepository {
    List<Team> fetchTeamsFromOrganization(Integer organizationId) throws HttpException;
}
