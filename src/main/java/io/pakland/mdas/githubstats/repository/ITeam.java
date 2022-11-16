package io.pakland.mdas.githubstats.repository;

import io.pakland.mdas.githubstats.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeam extends JpaRepository<Team,Long> {
}
