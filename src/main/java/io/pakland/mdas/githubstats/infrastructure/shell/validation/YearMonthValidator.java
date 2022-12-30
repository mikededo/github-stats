package io.pakland.mdas.githubstats.infrastructure.shell.validation;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YearMonthValidator implements InputValidator<String> {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy/MM");

    @Override
    public boolean validate(String input) {
        if (input == null || input.isBlank()) return false;

        YearMonth inputYearMonth = null;

        try {
            inputYearMonth = YearMonth.parse(input,dateTimeFormatter);
        }
        catch (DateTimeParseException e) {
            System.out.println("La fecha no tiene un formato correcto.");
            return false;
        }

        if(inputYearMonth.isBefore(YearMonth.now().plusMonths(1))) {
            System.out.println("No se puede consultar el mes actual o posterior.");
            return false;
        }

        return true;
    }

}
