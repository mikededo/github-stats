package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrganization extends JpaRepository<Organization,Integer> {
}
