package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Metric;

import java.io.IOException;
import java.util.List;

public interface MetricExporter {
    void export(List<Metric> metrics, String filePath) throws IOException;
}
