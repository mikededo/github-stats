package io.pakland.mdas.githubstats.infrastructure.shell.model;

import org.springframework.shell.command.CommandRegistration;

public class FromDateCommandRegistration implements CommandRegistrationBuilder {

    private final CommandRegistrationBuilder builder;

    public FromDateCommandRegistration(CommandRegistrationBuilder builder) {
        this.builder = builder;
    }

    @Override
    public CommandRegistration.Builder generate() {
        return this.builder
            .generate()
            .withOption()
            .longNames("from")
            .label("<from-date>")
            .description(
                "Date should be entered as MM/yy. We will parse the date to match the first day of the given month.")
            .arity(CommandRegistration.OptionArity.EXACTLY_ONE)
            .type(String.class)
            .required()
            .and();
    }
}
