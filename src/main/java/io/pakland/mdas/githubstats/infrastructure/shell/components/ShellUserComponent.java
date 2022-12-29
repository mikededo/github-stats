package io.pakland.mdas.githubstats.infrastructure.shell.components;

import io.pakland.mdas.githubstats.domain.enums.EntityType;
import io.pakland.mdas.githubstats.infrastructure.controller.MainController;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.DateValidator;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.UserNameValidator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellOption;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@ShellComponent
public class ShellUserComponent {

    private final MainController mainController;

    public ShellUserComponent(MainController mainController) {
        this.mainController = mainController;
    }

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

        ShellRequest shellRequest;
        try {
            shellRequest = ShellRequest.builder()
                .entityType(EntityType.USER)
                .name(userName)
                .apiKey(apiKey)
                .from(new SimpleDateFormat("dd/MM/yy").parse("01/" + fromDate))
                .to(new SimpleDateFormat("dd/MM/yy").parse("01/" + toDate))
                .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        mainController.execute(shellRequest);
        System.out.println("serializedOutput");

        // TODO : return console / file / etc.. output
        return true;
    }

}
