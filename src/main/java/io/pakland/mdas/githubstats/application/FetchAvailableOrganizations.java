package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.repository.OrganizationExternalRepository;

import java.util.List;

public class FetchAvailableOrganizations {

    private final OrganizationExternalRepository organizationExternalRepository;

    public FetchAvailableOrganizations(OrganizationExternalRepository organizationExternalRepository) {
        this.organizationExternalRepository = organizationExternalRepository;
    }

    public List<Organization> execute() throws HttpException {
        return organizationExternalRepository.fetchAvailableOrganizations();
    }
}
