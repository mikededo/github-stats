package io.pakland.mdas.githubstats.infrastructure.shell.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserNameValidatorTest {

    @Test
    void testValidation() {
        UserNameValidator userNameValidator = new UserNameValidator();

        assertTrue(userNameValidator.validate("name"));
        assertTrue(userNameValidator.validate("test-hello-world"));
        assertTrue(userNameValidator.validate("22312"));
        assertTrue(userNameValidator.validate("alphanumeric-and-hyphens-23"));
        assertTrue(userNameValidator.validate("UPPERcase"));

        assertFalse(userNameValidator.validate("name with spaces"));
        assertFalse(userNameValidator.validate("very-very-very-very-very-very-very-very-very-very-long-name"));
        assertFalse(userNameValidator.validate("-name"));
        assertFalse(userNameValidator.validate("name-"));
        assertFalse(userNameValidator.validate("i-have--2-hyphens"));
        assertFalse(userNameValidator.validate(""));
        assertFalse(userNameValidator.validate("  "));
        assertFalse(userNameValidator.validate(" - "));
    }

}
