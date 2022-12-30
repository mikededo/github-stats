package io.pakland.mdas.githubstats.infrastructure.shell.validation;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class YearMonthValidator implements InputValidator<String> {

    @Override
    public boolean validate(String input) {
        if (input == null || input.isBlank()) return false;

        YearMonth inputYearMonth;

        try {
            inputYearMonth = YearMonth.parse(input,getFormatter());
        }
        catch (DateTimeParseException e) {
            System.out.println("La fecha no tiene un formato correcto.");
            return false;
        }

        if(YearMonth.now().isBefore(inputYearMonth.plusMonths(1))) {
            System.out.println("No se puede consultar el mes actual o posterior.");
            return false;
        }

        return true;
    }

    public DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("MM/yy");
    }

}
