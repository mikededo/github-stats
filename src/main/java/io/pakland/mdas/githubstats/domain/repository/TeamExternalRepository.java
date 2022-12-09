package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;

import java.util.List;

public interface TeamExternalRepository {
    List<TeamDTO> fetchTeamsFromOrganization(Integer organizationId) throws HttpException;
}
