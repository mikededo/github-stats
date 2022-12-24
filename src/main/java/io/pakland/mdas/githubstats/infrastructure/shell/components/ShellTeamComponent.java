package io.pakland.mdas.githubstats.infrastructure.shell.components;

import io.pakland.mdas.githubstats.infrastructure.shell.validation.DateValidator;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.TeamNameValidator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShellTeamComponent {

    private boolean team(
            @ShellOption(value = {"n"}) String teamName,
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

        // ... Perform request ...

        return true;
    }

}
