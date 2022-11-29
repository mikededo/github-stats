package io.pakland.mdas.githubstats.domain.service;

import io.pakland.mdas.githubstats.domain.ports.OrganizationRepository;
import io.pakland.mdas.githubstats.domain.ports.UseCase;

public class GetOrganizationFromId implements UseCase {

    final OrganizationRepository organizationRepository;

    GetOrganizationFromId(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void execute() {
        organizationRepository.findById(1L);
    }
}
