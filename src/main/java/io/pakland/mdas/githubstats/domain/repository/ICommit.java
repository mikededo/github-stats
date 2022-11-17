package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommit extends JpaRepository<Commit,Integer> {
}
