package io.pakland.mdas.githubstats.infrastructure.shell.components;

import io.pakland.mdas.githubstats.domain.EntityType;
import io.pakland.mdas.githubstats.infrastructure.controller.MainController;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.YearMonthValidator;
import io.pakland.mdas.githubstats.infrastructure.shell.validation.NameValidator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellOption;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@ShellComponent
public class ShellController {

    private final MainController mainController;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy/MM");

    public ShellController(MainController mainController) {
        this.mainController = mainController;
    }

    private boolean user(
            @ShellOption(value = "name") String name,
            @ShellOption(value = "key") String apiKey,
            @ShellOption(value = "from") String from,
            @ShellOption(value = "to") String to,
            @ShellOption(value = "path", defaultValue = "") String path) {

        YearMonthValidator yearMonthValidator = new YearMonthValidator();
        NameValidator nameValidator = new NameValidator();

        boolean isInputValid =
                yearMonthValidator.validate(from) &&
                yearMonthValidator.validate(to) &&
                nameValidator.validate(name);

        if (!isInputValid) {
            return false;
        }

        YearMonth dateFrom = YearMonth.parse(from,dateTimeFormatter);
        YearMonth dateTo = YearMonth.parse(from,dateTimeFormatter);

        ShellRequest shellRequest = ShellRequest.builder()
                .entityType(EntityType.USER)
                .name(name)
                .apiKey(apiKey)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .build();

        mainController.execute(shellRequest);
        System.out.println("serializedOutput");

        // TODO : return console / file / etc.. output
        return true;
    }

}
