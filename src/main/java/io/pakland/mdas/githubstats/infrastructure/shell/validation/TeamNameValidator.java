package io.pakland.mdas.githubstats.infrastructure.shell.validation;

public class TeamNameValidator implements InputValidator<String> {

    public TeamNameValidator() {}

    /**
     * @param input Must pass the Github team name creation constraints:
     *      - Team name may only contain alphanumeric characters or hyphens.
     *      - Team name cannot have multiple consecutive hyphens.
     *      - Team name cannot begin or end with a hyphen.
     *      - Maximum is 39 characters.
     */
    @Override
    public boolean validate(String input) {
        if (input == null || input.isBlank()) return false;
        return !input.matches("^-.*")
                && !input.matches(".*-$")
                && input.matches("[a-zA-Z0-9\\-]{0,39}")
                && !input.matches(".*--.*");
    }

}
