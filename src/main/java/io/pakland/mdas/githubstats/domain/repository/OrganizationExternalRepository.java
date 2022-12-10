package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Organization;

import java.util.List;

public interface OrganizationExternalRepository {
    List<Organization> fetchAvailableOrganizations() throws HttpException;
}
