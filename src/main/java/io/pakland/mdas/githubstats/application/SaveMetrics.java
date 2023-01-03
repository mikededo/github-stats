package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Metric;
import io.pakland.mdas.githubstats.domain.repository.MetricRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaveMetrics {

    private final MetricRepository metricRepository;

    public SaveMetrics(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    @Transactional
    public void execute(List<Metric> metrics) {
        List<Metric> persistedMetrics = new ArrayList<>();

        metrics.forEach(metric-> {
            Optional<Metric> alreadyStoredMetric = metricRepository.getMetricUsingUniqueConstraint(metric);
            alreadyStoredMetric.ifPresent(persistedMetrics::add);
        });

        metricRepository.deleteAll(persistedMetrics);
        metricRepository.saveAll(metrics);
    }
}