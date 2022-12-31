package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.repository.MetricRepository;
import io.pakland.mdas.githubstats.infrastructure.postgres.model.PostgresOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Service;

@Service
public class CheckMetricsFromUserByRange {
    private final MetricRepository metricRepository;

    protected CheckMetricsFromUserByRange(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    public Boolean execute(PostgresOptionRequest req) {
        return metricRepository.countAggregatesFromOption(req).longValueExact() > 0;
    }

}