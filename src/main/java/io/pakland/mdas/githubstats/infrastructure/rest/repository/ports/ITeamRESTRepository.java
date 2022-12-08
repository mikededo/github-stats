package io.pakland.mdas.githubstats.infrastructure.rest.repository.ports;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;

import java.util.List;

public interface ITeamRESTRepository {
    List<TeamDTO> fetchTeamsFromOrganization(String organizationName) throws HttpException;
    List<UserDTO> fetchMembersOfTeam(String orgName, String teamSlug) throws HttpException;
}
