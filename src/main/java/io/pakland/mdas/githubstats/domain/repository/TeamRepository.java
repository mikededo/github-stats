package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Add jdoc about the rep
 */

@Repository
public interface TeamRepository extends JpaRepository<Team,Integer> {
    Optional<Team> findTeamBySlug(String name);
}
