package io.pakland.mdas.githubstats.infrastructure.controller;

import io.pakland.mdas.githubstats.infrastructure.github.controller.GitHubUserMiddleware;
import io.pakland.mdas.githubstats.infrastructure.postgres.PostgresMiddleware;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;

public class MainController {

    private final Middleware middleware;

    public MainController(ShellRequest request) {
        middleware = Middleware.link(
            new PostgresMiddleware(request),
            new GitHubUserMiddleware(request)
        );
    }

    public String execute() {
        return middleware.execute();
    }
}
