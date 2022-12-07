package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.IOrganizationRESTRepository;

import java.util.List;

public class FetchAvailableOrganizations {

    private IOrganizationRESTRepository organizationRESTRepository;

    public FetchAvailableOrganizations(IOrganizationRESTRepository organizationRESTRepository) {
        this.organizationRESTRepository = organizationRESTRepository;
    }

    public List<OrganizationDTO> fetch() throws HttpException {
        return organizationRESTRepository.fetchAvailableOrganizations();
    }
}
