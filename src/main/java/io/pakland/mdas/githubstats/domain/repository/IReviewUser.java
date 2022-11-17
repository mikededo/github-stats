package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.ReviewUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewUser extends JpaRepository<ReviewUser,Integer> {
}
