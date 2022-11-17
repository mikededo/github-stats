package io.pakland.mdas.githubstats.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepository extends JpaRepository<io.pakland.mdas.githubstats.domain.Repository,Integer> {
}
