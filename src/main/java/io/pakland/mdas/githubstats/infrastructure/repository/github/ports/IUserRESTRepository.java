package io.pakland.mdas.githubstats.infrastructure.repository.github.ports;

import io.pakland.mdas.githubstats.application.dto.UserDTO;

import java.util.List;

public interface IUserRESTRepository {
    List<UserDTO> getUsersFromTeam(String organizationName, String teamSlug);
}
