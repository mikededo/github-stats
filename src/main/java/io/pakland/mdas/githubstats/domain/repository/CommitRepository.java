package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.entity.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Add jdoc about the rep
 */

@Repository
public interface CommitRepository extends JpaRepository<Commit,Integer> {
}
