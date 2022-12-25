package io.pakland.mdas.githubstats.infrastructure.github.controller;

import io.pakland.mdas.githubstats.infrastructure.controller.Middleware;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubUserOptionRequest;

import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;

public class GitHubMiddleware extends Middleware {

    public GitHubMiddleware(ShellRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        GitHubController gitHubController = new GitHubController(
                GitHubUserOptionRequest.builder()
                        .userName(super.request.getName())
                        .apiKey(super.request.getApiKey())
                        .from(super.request.getFrom())
                        .to(super.request.getTo()).build());
        gitHubController.execute();

        return "csv from github";
    }
}
