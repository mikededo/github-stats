package io.pakland.mdas.githubstats.infrastructure.shell.validation;

import io.pakland.mdas.githubstats.domain.EntityType;

import java.util.Arrays;

public class EntityTypeValidator implements InputValidator<String> {

    public EntityTypeValidator() {}

    @Override
    public boolean validate(String input) {
        return Arrays.stream(EntityType.values()).anyMatch(e -> e.name().equals(input.toLowerCase()));
    }
}
