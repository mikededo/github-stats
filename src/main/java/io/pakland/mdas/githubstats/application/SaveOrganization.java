package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.repository.OrganizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SaveOrganization {
    public final OrganizationRepository organizationRepository;

    public SaveOrganization(OrganizationRepository repository) {
        this.organizationRepository = repository;
    }

    @Transactional
    public void execute(Organization organization) {
        Optional<Organization> found = organizationRepository.findById(organization.getId());
        if (found.isPresent()) {
            return;
        }
        organizationRepository.save(organization);
    }
}
