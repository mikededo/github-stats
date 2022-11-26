package io.pakland.mdas.githubstats.domain.ports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Add jdoc about the rep
 */

@Repository
public interface IRepository extends JpaRepository<io.pakland.mdas.githubstats.domain.model.Repository,Integer> {
}
