package io.pakland.mdas.githubstats.infrastructure.rest.repository.ports;

import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;

import java.util.List;

public interface IOrganizationRESTRepository {
    List<OrganizationDTO> fetchAvailableOrganizations();
}
