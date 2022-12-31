package io.pakland.mdas.githubstats.infrastructure.postgres.controller;

import io.pakland.mdas.githubstats.infrastructure.controller.Middleware;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.postgres.model.PostgresOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Controller;

@Controller
public class PostgresMiddleware extends Middleware {

    private final PostgresController postgresController;

    public PostgresMiddleware(PostgresController postgresController) {
        this.postgresController = postgresController;
    }

    @Override
    public String execute(ShellRequest request) {

        PostgresOptionRequest postgresOptionRequest = PostgresOptionRequest.builder()
                .name(request.getName())
                .apiKey(request.getApiKey())
                .type(request.getOptionType())
                .filePath(request.getFilePath())
                .silenced(request.isSilence())
                .from(request.getDateFrom())
                .to(request.getDateTo())
                .build();

        if (!postgresController.execute(postgresOptionRequest)) {
            super.checkNext(request);
        }

        return null;
    }
}