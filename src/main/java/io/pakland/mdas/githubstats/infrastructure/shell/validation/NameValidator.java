package io.pakland.mdas.githubstats.infrastructure.shell.validation;

public class NameValidator implements InputValidator<String> {

    public NameValidator() {}

    /**
     * @param input Must pass the Github username creation constraints:
     *      - Username may only contain alphanumeric characters or hyphens.
     *      - Username cannot have multiple consecutive hyphens.
     *      - Username cannot begin or end with a hyphen.
     *      - Maximum is 39 characters.
     */
    @Override
    public boolean validate(String input) {
        if (input == null || input.isBlank()) return false;
        return !input.matches("^-.*")
                && !input.matches(".*-$")
                && input.matches("[a-zA-Z\\d\\-]{0,39}")
                && !input.matches(".*--.*");
    }
}
