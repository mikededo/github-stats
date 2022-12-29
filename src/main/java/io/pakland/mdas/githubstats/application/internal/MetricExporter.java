package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.Metric;

import java.io.IOException;
import java.util.List;

public interface MetricExporter {
    void export(List<Metric> metrics, String filePath) throws IOException;
}
