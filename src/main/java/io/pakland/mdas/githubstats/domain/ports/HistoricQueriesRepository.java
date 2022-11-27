package io.pakland.mdas.githubstats.domain.ports;

import io.pakland.mdas.githubstats.domain.model.HistoricQueries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricQueriesRepository extends JpaRepository<HistoricQueries, Long> {
}