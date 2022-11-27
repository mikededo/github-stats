package io.pakland.mdas.githubstats.infrastructure.shell.validation;

public class TeamNameValidator implements InputValidator<String> {

    public TeamNameValidator() {}

    @Override
    public boolean validate(String input) {
        return input != null && !input.isBlank();
    }
}
