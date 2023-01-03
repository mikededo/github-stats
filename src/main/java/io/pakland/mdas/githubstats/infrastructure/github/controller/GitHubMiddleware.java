package io.pakland.mdas.githubstats.infrastructure.github.controller;

import io.pakland.mdas.githubstats.infrastructure.controller.Middleware;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import org.springframework.stereotype.Controller;

@Controller
public class GitHubMiddleware extends Middleware {

    private final GitHubController gitHubController;

    public GitHubMiddleware(GitHubController gitHubController) {
        this.gitHubController = gitHubController;
    }

    @Override
    public String execute(ShellRequest request) {
        gitHubController.execute(GitHubOptionRequest.builder()
                        .name(request.getName())
                        .apiKey(request.getApiKey())
                        .type(request.getOptionType())
                        .filePath(request.getFilePath())
                        .silenced(request.isSilence())
                        .from(request.transformYearMonthToDate(request.getDateFrom()))
                        .to(request.transformYearMonthToDate(request.getDateTo()))
                        .build());

        return String.format("- Generated results in %s", request.getFilePath());
    }
}
