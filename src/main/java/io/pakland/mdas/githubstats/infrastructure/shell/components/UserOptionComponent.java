package io.pakland.mdas.githubstats.infrastructure.shell.components;

import io.pakland.mdas.githubstats.infrastructure.shell.controller.UserOptionController;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.DateValidator;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.UserNameValidator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellOption;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@ShellComponent
public class UserOptionComponent {
    private UserOptionRequest userOptionRequest;

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

        try {
            this.userOptionRequest = UserOptionRequest.builder()
                    .userName(userName)
                    .apiKey(apiKey)
                    .from(new SimpleDateFormat("dd/MM/yy").parse("01/"+fromDate))
                    .to(new SimpleDateFormat("dd/MM/yy").parse("01/"+toDate))
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        UserOptionController userOptionController = new UserOptionController(this.userOptionRequest);
        userOptionController.execute();
        // ... Perform request ...

        return true;
    }

}
