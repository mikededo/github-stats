package io.pakland.mdas.githubstats.infrastructure.shell.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DateValidatorTest {
    DateValidator dateValidator = new DateValidator();

    @Test
    void testValidationShouldPass() {
        assertTrue(dateValidator.validate("12/01"));
        assertTrue(dateValidator.validate("03/22"));
        assertTrue(dateValidator.validate("09/22"));
        assertTrue(dateValidator.validate("10/20"));
        assertTrue(dateValidator.validate("07/15"));
    }

    @Test
    void testValidationShouldFail() {
        assertFalse(dateValidator.validate("aa"));
        assertFalse(dateValidator.validate(""));
        assertFalse(dateValidator.validate("01-02"));
        assertFalse(dateValidator.validate("13/9"));
        assertFalse(dateValidator.validate("00/00"));
        assertFalse(dateValidator.validate("1/4444444444444"));
        assertFalse(dateValidator.validate("2/22"));
        assertFalse(dateValidator.validate("  /  "));
    }
}
