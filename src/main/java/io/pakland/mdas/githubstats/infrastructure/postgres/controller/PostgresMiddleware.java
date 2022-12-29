package io.pakland.mdas.githubstats.infrastructure.postgres.controller;

import io.pakland.mdas.githubstats.application.internal.CheckMetricsFromUserByRange;
import io.pakland.mdas.githubstats.application.internal.GetMetricsFromUserByRange;
import io.pakland.mdas.githubstats.domain.entity.Metric;
import io.pakland.mdas.githubstats.infrastructure.controller.Middleware;
import io.pakland.mdas.githubstats.infrastructure.csv.CsvAggregationFileProcessor;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PostgresMiddleware extends Middleware {


    private final CheckMetricsFromUserByRange checkMetricsFromUserByRange;

    private final GetMetricsFromUserByRange getMetricsFromUser;

    private final CsvAggregationFileProcessor csvAggregationFileProcessor;

    protected PostgresMiddleware(CheckMetricsFromUserByRange checkMetricsFromUserByRange, GetMetricsFromUserByRange getMetricsFromUser, CsvAggregationFileProcessor csvAggregationFileProcessor) {
        this.checkMetricsFromUserByRange = checkMetricsFromUserByRange;
        this.getMetricsFromUser = getMetricsFromUser;
        this.csvAggregationFileProcessor = csvAggregationFileProcessor;
    }

    @Override
    public String execute(ShellRequest request) {

        if (checkMetricsFromUserByRange.execute(request)) {
            List<Metric> metrics = getMetricsFromUser.execute(request);
            csvAggregationFileProcessor.write("prueba.csv", metrics);
            return "csv from db";
        }
       
        return super.checkNext(request);
    }
}