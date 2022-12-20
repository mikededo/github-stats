package io.pakland.mdas.githubstats.infrastructure.github.handlers.request;

import io.pakland.mdas.githubstats.application.handlers.Request;
import io.pakland.mdas.githubstats.domain.entity.Organization;

public class OrganizationRequest implements Request {

    private final Organization organization;

    public OrganizationRequest(Organization organization) {
        this.organization = organization;
    }

    @Override
    public Organization getData() {
        return organization;
    }
}
