package io.pakland.mdas.githubstats.infrastructure.github.controller;

import io.pakland.mdas.githubstats.infrastructure.controller.Middleware;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubUserOptionRequest;

import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Component;

public class GitHubUserMiddleware extends Middleware {

    public GitHubUserMiddleware(ShellRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        GitHubUserController gitHubUserController = new GitHubUserController(
                GitHubUserOptionRequest.builder()
                        .userName(super.request.getName())
                        .apiKey(super.request.getApiKey())
                        .from(super.request.getFrom())
                        .to(super.request.getTo()).build());
        gitHubUserController.execute();

        return "csv from github";
    }
}
