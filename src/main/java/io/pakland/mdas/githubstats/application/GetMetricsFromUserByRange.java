package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Metric;
import io.pakland.mdas.githubstats.domain.repository.MetricRepository;
import io.pakland.mdas.githubstats.infrastructure.postgres.model.PostgresOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMetricsFromUserByRange {

    private final MetricRepository metricRepository;

    public GetMetricsFromUserByRange(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    public List<Metric> execute(PostgresOptionRequest request) {
        return metricRepository.getAggregatesFromOption(request);
    }
}