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
     * @param input Must be MM/yy, must be prior to the current month
     */
    @Override
    public boolean validate(String input) {
        if (input == null || input.isBlank()) return false;

        try {
            LocalDateTime date = LocalDateTime.parse("01/" + input + " 00:00", formatter);
            LocalDateTime now = LocalDateTime.now();
            return date.isBefore(now.minusMonths(1));
        } catch (DateTimeParseException e) {
            // Input was not properly formatted
            return false;
        }
    }

}
