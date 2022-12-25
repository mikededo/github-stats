package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Team;

import java.util.List;

public interface TeamExternalRepository {
    List<Team> fetchTeamsFromOrganization(Organization organization) throws HttpException;
}
