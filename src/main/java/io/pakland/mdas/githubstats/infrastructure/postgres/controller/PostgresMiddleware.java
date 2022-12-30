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
    public void execute(ShellRequest request) {

        if (checkMetricsFromUserByRange.execute(request)) {
            List<Metric> metrics = getMetricsFromUser.execute(request);

            try {
                metricExporter.export(metrics, "prueba.csv");
            } catch (IOException e) {
                System.out.println("Error writing csv file");
            }
            return;
        }
       
        super.checkNext(request);
    }
}