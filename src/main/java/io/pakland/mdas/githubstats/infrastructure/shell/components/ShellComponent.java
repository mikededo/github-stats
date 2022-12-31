package io.pakland.mdas.githubstats.infrastructure.shell.components;


import io.pakland.mdas.githubstats.domain.OptionType;
import io.pakland.mdas.githubstats.infrastructure.controller.MainController;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.NameValidator;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.YearMonthValidator;
import org.springframework.shell.standard.ShellOption;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@org.springframework.shell.standard.ShellComponent
public abstract class ShellComponent {

    private final OptionType optionType;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected ShellComponent(OptionType optionType) {
       this.optionType = optionType;
    }

    private boolean run(
        @ShellOption(value = {"n"}) String userName,
        @ShellOption(value = {"key"}) String apiKey,
        @ShellOption(value = {"from"}) String fromDate,
        @ShellOption(value = {"to"}) String toDate
    ) {
        YearMonthValidator yearMonthValidator = new YearMonthValidator();
        NameValidator nameValidator = new NameValidator();

        boolean isInputValid =
            yearMonthValidator.validate(fromDate) &&
            yearMonthValidator.validate(toDate) &&
            nameValidator.validate(userName);

        if (!isInputValid) {
            return false;
        }

        YearMonth dateFrom = YearMonth.parse(fromDate,yearMonthValidator.getFormatter());
        YearMonth dateTo = YearMonth.parse(toDate,yearMonthValidator.getFormatter());

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
