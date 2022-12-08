package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;

import java.util.List;

public interface OrganizationExternalRepository {
    List<OrganizationDTO> fetchAvailableOrganizations() throws HttpException;
}
