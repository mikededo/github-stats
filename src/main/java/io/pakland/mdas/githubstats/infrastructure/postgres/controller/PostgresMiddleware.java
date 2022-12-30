package io.pakland.mdas.githubstats.infrastructure.postgres.controller;

import io.pakland.mdas.githubstats.application.MetricExporter;
import io.pakland.mdas.githubstats.application.internal.CheckMetricsFromUserByRange;
import io.pakland.mdas.githubstats.application.internal.GetMetricsFromUserByRange;
import io.pakland.mdas.githubstats.domain.Metric;
import io.pakland.mdas.githubstats.infrastructure.controller.Middleware;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class PostgresMiddleware extends Middleware {


    private final CheckMetricsFromUserByRange checkMetricsFromUserByRange;

    private final GetMetricsFromUserByRange getMetricsFromUser;

    private final MetricExporter metricExporter;

    protected PostgresMiddleware(CheckMetricsFromUserByRange checkMetricsFromUserByRange,
                                 GetMetricsFromUserByRange getMetricsFromUser,
                                 MetricExporter metricExporter) {
        this.checkMetricsFromUserByRange = checkMetricsFromUserByRange;
        this.getMetricsFromUser = getMetricsFromUser;
        this.metricExporter = metricExporter;
    }

    @Override
    public String execute(ShellRequest request) {

        if (checkMetricsFromUserByRange.execute(request)) {
            List<Metric> metrics = getMetricsFromUser.execute(request);

            try {
                metricExporter.export(metrics, "prueba.csv");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return "csv from db";
        }
       
        return super.checkNext(request);
    }
}