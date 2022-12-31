package io.pakland.mdas.githubstats.infrastructure.shell.model;

import org.springframework.shell.command.CommandRegistration;

public interface CommandRegistrationBuilder {
    CommandRegistration.Builder generate();
}
