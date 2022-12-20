package io.pakland.mdas.githubstats.infrastructure.shell.components;


import io.pakland.mdas.githubstats.infrastructure.github.controller.GitHubUserOptionController;
import io.pakland.mdas.githubstats.infrastructure.github.model.GitHubUserOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.DateValidator;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.UserNameValidator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UserOptionComponent {

    private boolean user(
        @ShellOption(value = {"n"}) String userName,
        @ShellOption(value = {"key"}) String apiKey,
        @ShellOption(value = {"from"}) String fromDate,
        @ShellOption(value = {"to"}) String toDate
    ) {
        DateValidator dateValidator = new DateValidator();
        UserNameValidator userNameValidator = new UserNameValidator();

        boolean isInputValid = dateValidator.validate(fromDate)
            && dateValidator.validate(toDate)
            && userNameValidator.validate(userName);

        if (!isInputValid) {
            // Alert user, and halt command
            return false;
        }

        UserOptionRequest userOptionRequest;
        try {
            userOptionRequest = UserOptionRequest.builder()
                .userName(userName)
                .apiKey(apiKey)
                .from(new SimpleDateFormat("dd/MM/yy").parse("01/" + fromDate))
                .to(new SimpleDateFormat("dd/MM/yy").parse("01/" + toDate))
                .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        GitHubUserOptionController userControllerFromGithub = new GitHubUserOptionController(
            GitHubUserOptionRequest.builder()
                .userName(userOptionRequest.getUserName()).apiKey(userOptionRequest.getApiKey())
                .from(userOptionRequest.getFrom()).to(userOptionRequest.getTo()).build());

        userControllerFromGithub.execute();

        return true;
    }

}
