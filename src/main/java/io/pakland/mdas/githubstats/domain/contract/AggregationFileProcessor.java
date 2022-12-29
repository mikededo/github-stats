package io.pakland.mdas.githubstats.domain.contract;

import io.pakland.mdas.githubstats.domain.entity.Metric;

import java.util.List;

public interface AggregationFileProcessor {

    void write(String path, List<Metric> metrics);

}
