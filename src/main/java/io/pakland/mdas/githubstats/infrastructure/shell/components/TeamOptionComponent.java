package io.pakland.mdas.githubstats.infrastructure.shell.components;

import io.pakland.mdas.githubstats.infrastructure.github.controller.GitHubUserOptionController;
import io.pakland.mdas.githubstats.infrastructure.github.controller.GithubTeamOptionController;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubUserOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.github.model.GithubTeamOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.model.TeamOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.DateValidator;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.TeamNameValidator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellOption;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@ShellComponent
public class TeamOptionComponent {

    private boolean team(
            @ShellOption(value = {"n"}) String teamName,
            @ShellOption(value = {"key"}) String apiKey,
            @ShellOption(value = {"from"}) String fromDate,
            @ShellOption(value = {"to"}) String toDate
    ) {
        DateValidator dateValidator = new DateValidator();
        TeamNameValidator teamNameValidator = new TeamNameValidator();

        boolean isInputValid = dateValidator.validate(fromDate)
                && dateValidator.validate(toDate)
                && teamNameValidator.validate(teamName);

        if (!isInputValid) {
            // Alert user, and halt command
            return false;
        }

        TeamOptionRequest teamOptionRequest;
        try {
            teamOptionRequest = TeamOptionRequest.builder()
                .teamName(teamName)
                .apiKey(apiKey)
                .from(new SimpleDateFormat("dd/MM/yy").parse("01/" + fromDate))
                .to(new SimpleDateFormat("dd/MM/yy").parse("01/" + toDate))
                .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        GithubTeamOptionController teamControllerFromGithub = new GithubTeamOptionController(
                GithubTeamOptionRequest.builder()
                    .teamName(teamOptionRequest.getTeamName())
                    .apiKey(teamOptionRequest.getApiKey())
                    .from(teamOptionRequest.getFrom())
                    .to(teamOptionRequest.getTo())
                .build());

        teamControllerFromGithub.execute();

        return true;
    }

}
