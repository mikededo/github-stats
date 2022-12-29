package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.entity.Metric;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetricRepository {

    Long countUserMonthsFromRange(ShellRequest req);

    List<Metric> findUserMetricsFromRange(ShellRequest req);

}