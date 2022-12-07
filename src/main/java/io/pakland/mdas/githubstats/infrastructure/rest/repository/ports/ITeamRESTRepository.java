package io.pakland.mdas.githubstats.infrastructure.rest.repository.ports;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;

import java.util.List;

public interface ITeamRESTRepository {
    List<TeamDTO> fetchTeamsFromOrganization(String organizationName);
}
