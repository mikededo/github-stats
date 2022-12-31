package io.pakland.mdas.githubstats.infrastructure.shell.components;


import io.pakland.mdas.githubstats.domain.OptionType;
import io.pakland.mdas.githubstats.infrastructure.controller.MainController;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.DateValidator;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.UserNameValidator;
import org.springframework.shell.standard.ShellOption;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@org.springframework.shell.standard.ShellComponent
public abstract class ShellComponent {

    private final OptionType optionType;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected ShellComponent(OptionType optionType) {
       this.optionType = optionType;
    }

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/yy");

    private boolean run(
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
            return false;
        }

        YearMonth dateFrom = YearMonth.parse(fromDate,dateTimeFormatter);
        YearMonth dateTo = YearMonth.parse(toDate,dateTimeFormatter);

        ShellRequest shellRequest;
        shellRequest = ShellRequest.builder()
            .entityType(this.optionType)
            .name(userName)
            .apiKey(apiKey)
            .dateFrom(dateFrom)
            .dateTo(dateTo)
            .build();

        MainController main = new MainController(shellRequest);
        String serializedOutput = main.execute();
        System.out.println(serializedOutput);

        return true;
    }

}
