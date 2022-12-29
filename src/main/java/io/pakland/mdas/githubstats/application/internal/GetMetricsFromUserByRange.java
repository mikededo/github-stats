package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.Metric;
import io.pakland.mdas.githubstats.infrastructure.postgres.repository.PostgresMetricRepository;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMetricsFromUserByRange {

    private final PostgresMetricRepository postgresMetricRepository;

    public GetMetricsFromUserByRange(PostgresMetricRepository postgresMetricRepository) {
        this.postgresMetricRepository = postgresMetricRepository;
    }

    public List<Metric> execute(ShellRequest request) {
        return postgresMetricRepository.findUserMetricsFromRange(request);
    }
}
