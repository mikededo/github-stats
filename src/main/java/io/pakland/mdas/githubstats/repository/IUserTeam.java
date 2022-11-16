package io.pakland.mdas.githubstats.repository;

import io.pakland.mdas.githubstats.model.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserTeam extends JpaRepository<UserTeam,Long> {
}
