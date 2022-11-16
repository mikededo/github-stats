package io.pakland.mdas.githubstats.repository;

import io.pakland.mdas.githubstats.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrganization extends JpaRepository<Organization,Long> {
}
