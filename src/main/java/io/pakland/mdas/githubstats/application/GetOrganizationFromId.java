package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.OrganizationNotFound;
import io.pakland.mdas.githubstats.domain.Organization;
import io.pakland.mdas.githubstats.domain.repository.OrganizationRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GetOrganizationFromId {

    final private OrganizationRepository organizationRepository;

    public GetOrganizationFromId(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    // For tests sake, we return boolean to know if the code works properly
    @Transactional(readOnly = true)
    public boolean execute(Long id) throws OrganizationNotFound {
        Optional<Organization> org = organizationRepository.findById(id);
        if (org.isPresent()) {
            return true;
        }
        else {
            throw new OrganizationNotFound("Organization with id " + id + " not found.");
        }
    }
}
