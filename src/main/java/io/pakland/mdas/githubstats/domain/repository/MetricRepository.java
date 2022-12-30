package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.Metric;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;

import java.util.List;
import java.util.Date;

public interface MetricRepository {

    Long countUserMonthsFromRange(ShellRequest req);

    List<Metric> findUserMetricsFromRange(ShellRequest req);

}