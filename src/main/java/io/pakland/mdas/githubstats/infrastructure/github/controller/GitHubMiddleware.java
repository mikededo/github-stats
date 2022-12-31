package io.pakland.mdas.githubstats.infrastructure.github.controller;

import io.pakland.mdas.githubstats.infrastructure.controller.Middleware;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;

public class GitHubMiddleware extends Middleware {

    public GitHubMiddleware(ShellRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        GitHubController gitHubController = new GitHubController(
            GitHubOptionRequest.builder()
                .name(super.request.getName())
                .apiKey(super.request.getApiKey())
                .type(super.request.getEntityType())
                .from(super.request.transformYearMonthToDate(request.getDateFrom()))
                .to(super.request.transformYearMonthToDate(request.getDateTo()))
                .filePath(super.request.getFilePath())
                .build());
        gitHubController.execute();

        return "csv from github";
    }
}
