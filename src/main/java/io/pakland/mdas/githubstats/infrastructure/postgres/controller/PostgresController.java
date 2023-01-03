package io.pakland.mdas.githubstats.infrastructure.postgres.controller;

import io.pakland.mdas.githubstats.application.CheckMetricsFromUserByRange;
import io.pakland.mdas.githubstats.application.ExportMetricsToFile;
import io.pakland.mdas.githubstats.application.GetMetricsFromUserByRange;
import io.pakland.mdas.githubstats.domain.Metric;
import io.pakland.mdas.githubstats.domain.OptionType;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.GitHubMetricCsvExporter;
import io.pakland.mdas.githubstats.infrastructure.postgres.model.PostgresOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class PostgresController {

    private PostgresOptionRequest postgresOptionRequest = null;

    private final CheckMetricsFromUserByRange checkMetricsFromUserByRange;

    private final GetMetricsFromUserByRange getMetricsFromUser;

    public PostgresController(CheckMetricsFromUserByRange checkMetricsFromUserByRange, GetMetricsFromUserByRange getMetricsFromUser) {
        this.checkMetricsFromUserByRange = checkMetricsFromUserByRange;
        this.getMetricsFromUser = getMetricsFromUser;
    }

    public Boolean execute(PostgresOptionRequest request) {
        this.postgresOptionRequest = request;

        if (checkMetricsFromUserByRange.execute(request)) {
            logIfNotSilenced("- Stats are already stored at database...");
            logIfNotSilenced("- Retrieving data from database...");
            List<Metric> metrics = getMetricsFromUser.execute(request);

            logIfNotSilenced("- " +metrics.size()+" rows were read from database");

            try {
                logIfNotSilenced("- Preparing CSV...");
                new ExportMetricsToFile(new GitHubMetricCsvExporter())
                    .execute(metrics, request.getFilePath());
                logIfNotSilenced("- File "+request.getFilePath()+" saved successfully ");
            } catch (IOException e) {
                System.out.println("Error writing "+request.getFilePath()+" file");
            }
            return true;
        }

        return false;
    }

    private boolean isLoggerSilenced() {
        return this.postgresOptionRequest.isSilenced();
    }

    private void logIfNotSilenced(String text) {
        if (!isLoggerSilenced()) {
            System.out.println(text);
        }
    }

}
