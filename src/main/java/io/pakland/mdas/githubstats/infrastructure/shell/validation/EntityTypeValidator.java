package io.pakland.mdas.githubstats.infrastructure.shell.validation;

import io.pakland.mdas.githubstats.domain.EntityType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EntityTypeValidator implements InputValidator<String> {

    public EntityTypeValidator() {}

    @Override
    public boolean validate(String input) {
        boolean valid = Arrays.stream(EntityType.values())
                .map(EntityType::getEntity)
                .anyMatch(e -> e.equals(input.toLowerCase()));
        if(!valid) {
            System.out.println("El tipo de entidad no es válido: "+ Arrays.stream(EntityType.values()).map(EntityType::getEntity).toList());
        }
        return valid;
    }
}
