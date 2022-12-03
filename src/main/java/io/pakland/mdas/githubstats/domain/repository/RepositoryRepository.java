package io.pakland.mdas.githubstats.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Add jdoc about the rep
 */

@Repository
public interface RepositoryRepository extends JpaRepository<io.pakland.mdas.githubstats.domain.Repository,Long> {
}
