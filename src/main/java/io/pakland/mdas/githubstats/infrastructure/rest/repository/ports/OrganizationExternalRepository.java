package io.pakland.mdas.githubstats.infrastructure.rest.repository.ports;

import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;

import java.util.List;

public interface OrganizationExternalRepository {
    List<OrganizationDTO> fetchAvailableOrganizations() throws HttpException;
}
