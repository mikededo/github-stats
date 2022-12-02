package io.pakland.mdas.githubstats.infrastructure.repository.rest.ports;

import io.pakland.mdas.githubstats.infrastructure.repository.rest.dto.UserDTO;

import java.util.List;

public interface IUserRESTRepository {
    public List<UserDTO> getUsersFromTeam(String organizationName, String teamSlug);
}
