package io.pakland.mdas.githubstats.infrastructure.rest.repository.ports;

import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;

import java.util.List;

public interface IUserRESTRepository {
    List<UserDTO> fetchUsersFromTeam(String organizationName, String teamSlug) throws HttpException;
}
