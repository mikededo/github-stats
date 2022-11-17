package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.Commit;
import io.pakland.mdas.githubstats.domain.PullRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPullRequests extends JpaRepository<PullRequests,Integer> {
}
