package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.HistoricQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends JpaRepository<HistoricQueries, Integer> {
}