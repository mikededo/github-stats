package io.pakland.mdas.githubstats.infrastructure.shell.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeamNameValidatorTest {
    TeamNameValidator teamNameValidator = new TeamNameValidator();

    @Test
    void testValidationShouldPass() {
        assertTrue(teamNameValidator.validate("name"));
        assertTrue(teamNameValidator.validate("test-hello-world"));
        assertTrue(teamNameValidator.validate("22312"));
        assertTrue(teamNameValidator.validate("alphanumeric-and-hyphens-23"));
        assertTrue(teamNameValidator.validate("UPPERcase"));
    }

    @Test
    void testValidationShouldFail() {
        assertFalse(teamNameValidator.validate("name with spaces"));
        assertFalse(teamNameValidator.validate("very-very-very-very-very-very-very-very-very-very-long-name"));
        assertFalse(teamNameValidator.validate("-name"));
        assertFalse(teamNameValidator.validate("name-"));
        assertFalse(teamNameValidator.validate("i-have--2-hyphens"));
        assertFalse(teamNameValidator.validate(""));
        assertFalse(teamNameValidator.validate("  "));
        assertFalse(teamNameValidator.validate(" - "));
    }
}
