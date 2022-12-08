package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.repository.OrganizationExternalRepository;

import java.util.List;

public class FetchAvailableOrganizations {

    private OrganizationExternalRepository organizationRESTRepository;

    public FetchAvailableOrganizations(OrganizationExternalRepository organizationRESTRepository) {
        this.organizationRESTRepository = organizationRESTRepository;
    }

    public List<OrganizationDTO> fetch() throws HttpException {
        return organizationRESTRepository.fetchAvailableOrganizations();
    }
}
