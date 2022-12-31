package io.pakland.mdas.githubstats.infrastructure.shell.model;

import org.springframework.shell.command.CommandRegistration;

public class LoggerSilencerCommandRegistration implements CommandRegistrationBuilder {

    private final CommandRegistrationBuilder builder;

    public LoggerSilencerCommandRegistration(CommandRegistrationBuilder builder) {
        this.builder = builder;
    }

    @Override
    public CommandRegistration.Builder generate() {
        return this.builder
            .generate()
            .withOption()
            .longNames("silence")
            .description("Silence the logs outputted to the console.")
            .type(Boolean.class)
            .defaultValue("false")
            .and();
    }
}
