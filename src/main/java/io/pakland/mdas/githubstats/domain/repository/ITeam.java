package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Add jdoc about the rep
 */

@Repository
public interface ITeam extends JpaRepository<Team,Integer> {
}
