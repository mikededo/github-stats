package io.pakland.mdas.githubstats.domain.ports;

import io.pakland.mdas.githubstats.domain.model.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Add jdoc about the rep
 */

@Repository
public interface ReviewUserRepository extends JpaRepository<UserReview,Long> {
}
