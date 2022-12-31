package io.pakland.mdas.githubstats.infrastructure.shell.model;

import org.springframework.shell.command.CommandRegistration;

public class ToDateCommandRegistration implements CommandRegistrationBuilder {

    private final CommandRegistrationBuilder builder;

    public ToDateCommandRegistration(CommandRegistrationBuilder builder) {
        this.builder = builder;
    }

    @Override
    public CommandRegistration.Builder generate() {
        return this.builder
            .generate()
            .withOption()
            .longNames("to")
            .label("<to-date>")
            .description(
                "Date should be entered as MM/yy. We will parse the date to match the first day of the given month.")
            .arity(CommandRegistration.OptionArity.EXACTLY_ONE)
            .type(String.class)
            .required()
            .and();
    }
}
