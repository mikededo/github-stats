package io.pakland.mdas.githubstats.infrastructure.github.controller;

import io.pakland.mdas.githubstats.infrastructure.controller.Middleware;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubUserOptionRequest;

import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Controller;

@Controller
public class GitHubMiddleware extends Middleware {

    @Override
    public String execute(ShellRequest request) {
        GitHubController gitHubController = new GitHubController(
                GitHubUserOptionRequest.builder()
                        .userName(request.getName())
                        .apiKey(request.getApiKey())

                        // TODO : temporal workarround
                        .from(java.sql.Date.valueOf(request.getDateFrom().atDay(1)))
                        .to(java.sql.Date.valueOf(request.getDateTo().atDay(1)))
                        .build());

        gitHubController.execute();
        return "csv from github";
    }
}
