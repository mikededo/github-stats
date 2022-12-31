package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.Metric;

import java.io.IOException;
import java.util.List;

public interface FileMetricExporter {
    void export(List<Metric> metrics, String filePath) throws IOException;
}
