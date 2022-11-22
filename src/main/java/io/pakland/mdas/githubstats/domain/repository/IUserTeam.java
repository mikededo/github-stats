package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Add jdoc about the rep
 */

@Repository
public interface IUserTeam extends JpaRepository<UserTeam,Integer> {
}
