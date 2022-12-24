package io.pakland.mdas.githubstats.infrastructure.controller;

import io.pakland.mdas.githubstats.infrastructure.github.controller.GitHubUserMiddleware;
import io.pakland.mdas.githubstats.infrastructure.postgres.PostgresMiddleware;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;

import java.sql.SQLOutput;

public class MainController {

    private final Middleware middleware;

    private final ShellRequest request;

    public MainController(ShellRequest request) {
        this.request = request;
        middleware = Middleware.link(
                new PostgresMiddleware(request),
                new GitHubUserMiddleware(request)
        );
    }

    public String execute() {
        return middleware.execute();
    }
}
