package io.pakland.mdas.githubstats.infrastructure.controller;

import io.pakland.mdas.githubstats.infrastructure.github.controller.GitHubMiddleware;
import io.pakland.mdas.githubstats.infrastructure.postgres.controller.PostgresMiddleware;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    private final Middleware middleware;

    public MainController(PostgresMiddleware postgresMiddleware, GitHubMiddleware gitHubMiddleware) {
        middleware = Middleware.link( postgresMiddleware, gitHubMiddleware);
    }

    public void execute(ShellRequest request) {
        middleware.execute(request);
    }
}
