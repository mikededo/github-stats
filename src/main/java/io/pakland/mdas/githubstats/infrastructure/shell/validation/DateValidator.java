package io.pakland.mdas.githubstats.infrastructure.shell.validation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements InputValidator<String> {

    private final DateTimeFormatter formatter;

    public DateValidator() {
        String datePattern = "dd/MM/yy HH:mm";
        formatter = DateTimeFormatter.ofPattern(datePattern);
    }

    /**
     * @param input Must be MM/yy, and prior to the current month. Starting year: 2000 (01/01/99 parses to 01/01/2099)
     */
    @Override
    public boolean validate(String input) {
        if (input == null || input.isBlank()) return false;

        try {
            LocalDateTime date = LocalDateTime.parse(getInitialDateFromMMYY(input), formatter);
            LocalDateTime now = LocalDateTime.now();
            return date.isBefore(now.minusMonths(1));
        } catch (DateTimeParseException e) {
            // Input was not properly formatted
            return false;
        }
    }

    private String getInitialDateFromMMYY(String input) {
        return "01/" + input + " 00:00";
    }

}
