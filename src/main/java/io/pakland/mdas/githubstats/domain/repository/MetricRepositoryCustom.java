package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.Metric;
import io.pakland.mdas.githubstats.infrastructure.postgres.model.PostgresOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface MetricRepositoryCustom {
    Optional<Metric> getMetricUsingUniqueConstraint(Metric metric);

    BigInteger countAggregatesFromOption(PostgresOptionRequest req);

    List<Metric> getAggregatesFromOption(PostgresOptionRequest req);

}