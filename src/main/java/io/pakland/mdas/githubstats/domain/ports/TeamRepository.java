package io.pakland.mdas.githubstats.domain.ports;

import io.pakland.mdas.githubstats.domain.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Add jdoc about the rep
 */

@Repository
public interface TeamRepository extends JpaRepository<Team,Integer> {
}
