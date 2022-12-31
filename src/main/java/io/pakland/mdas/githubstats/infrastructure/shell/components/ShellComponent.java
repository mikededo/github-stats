package io.pakland.mdas.githubstats.infrastructure.shell.components;


import io.pakland.mdas.githubstats.domain.OptionType;
import io.pakland.mdas.githubstats.infrastructure.controller.MainController;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.DateValidator;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.UserNameValidator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.shell.standard.ShellOption;

@org.springframework.shell.standard.ShellComponent
public abstract class ShellComponent {

    private boolean run(
        @ShellOption(value = {"n"}) String userName,
        @ShellOption(value = {"key"}) String apiKey,
        @ShellOption(value = {"from"}) String fromDate,
        @ShellOption(value = {"to"}) String toDate,
        @ShellOption(value = {"path"}) String path,
        @ShellOption(value = {"silence"}) String silence
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
                .entityType(getType())
                .name(userName)
                .apiKey(apiKey)
                .from(new SimpleDateFormat("dd/MM/yy").parse("01/" + fromDate))
                .to(new SimpleDateFormat("dd/MM/yy").parse("01/" + toDate))
                .filePath(path)
                .silence(silence == "true")
                .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        MainController main = new MainController(shellRequest);
        String serializedOutput = main.execute();
        System.out.println(serializedOutput);

        // TODO : return console / file / etc.. output
        return true;
    }

    abstract OptionType getType();

}
