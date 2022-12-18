package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.application.exceptions.OrganizationNotFound;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.repository.OrganizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GetOrganizationFromId {

    private final OrganizationRepository organizationRepository;

    public GetOrganizationFromId(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    // For tests sake, we return boolean to know if the code works properly
    @Transactional(readOnly = true)
    public Organization execute(Integer id) throws OrganizationNotFound {
        Optional<Organization> org = organizationRepository.findById(id);
        if(org.isEmpty()) {
            throw new OrganizationNotFound(id);
        }
        return org.get();
    }
}
