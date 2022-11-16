package io.pakland.mdas.githubstats.repository;

import io.pakland.mdas.githubstats.model.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserOrganization extends JpaRepository<UserOrganization,Long> {
}
