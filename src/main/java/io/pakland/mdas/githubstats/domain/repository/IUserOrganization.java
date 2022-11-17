package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserOrganization extends JpaRepository<UserOrganization,Integer> {
}
