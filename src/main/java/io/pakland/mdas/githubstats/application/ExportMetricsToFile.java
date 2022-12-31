package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Metric;
import io.pakland.mdas.githubstats.domain.repository.FileMetricExporter;
import java.io.IOException;
import java.util.List;

public class ExportMetricsToFile {
    private final FileMetricExporter exporter;

    public ExportMetricsToFile(FileMetricExporter exporter) {
        this.exporter = exporter;
    }

    public void execute(List<Metric> metrics, String filePath) throws IOException {
        this.exporter.export(metrics, filePath);
    }
}
