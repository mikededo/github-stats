package io.pakland.mdas.githubstats.infrastructure.shell.components;

import io.pakland.mdas.githubstats.infrastructure.shell.validation.DateValidator;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.UserNameValidator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UserComponent {
    
    private boolean user(
            @ShellOption(value = {"n"}) String userName,
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

        // ... Perform request ...

        return true;
    }

}
