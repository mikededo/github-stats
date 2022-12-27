package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.Metric;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MetricCsvExporter implements MetricExporter {
    @Override
    public void export(List<Metric> metrics, String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("id")
            .append(",").append("organization")
            .append(",").append("team_slug")
            .append(",").append("user_name")
            .append(",").append("merged_pulls")
            .append(",").append("internal_reviews")
            .append(",").append("external_reviews")
            .append(",").append("comments_avg_length")
            .append(",").append("commits_count")
            .append(",").append("lines_added")
            .append(",").append("lines_removed")
            .append(",").append("from")
            .append(",").append("to")
            .append("\n");

        for (Metric metric : metrics) {
            sb.append(metric.getId())
                .append(",").append(metric.getOrganization())
                .append(",").append(metric.getTeamSlug())
                .append(",").append(metric.getUserName())
                .append(",").append(metric.getMergedPulls().toString())
                .append(",").append(metric.getInternalReviews().toString())
                .append(",").append(metric.getExternalReviews().toString())
                .append(",").append(metric.getCommentsAvgLength().toString())
                .append(",").append(metric.getCommitsCount().toString())
                .append(",").append(metric.getLinesAdded().toString())
                .append(",").append(metric.getLinesRemoved().toString())
                .append(",").append(metric.getFrom().toString())
                .append(",").append(metric.getTo().toString())
                .append("\n");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write(sb.toString());
        }

    }
}
