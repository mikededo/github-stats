package io.pakland.mdas.githubstats.domain.service;

import io.pakland.mdas.githubstats.domain.model.Organization;
import io.pakland.mdas.githubstats.domain.ports.OrganizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GetOrganizationFromId {

    final private OrganizationRepository organizationRepository;

    public GetOrganizationFromId(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Transactional(readOnly = true)
    public void execute(Long id) {
        Optional<Organization> org = organizationRepository.findById(id);
        if (org.isPresent())
            System.out.println(org.get());
        else
            System.out.println("Organization not found");
    }
}
