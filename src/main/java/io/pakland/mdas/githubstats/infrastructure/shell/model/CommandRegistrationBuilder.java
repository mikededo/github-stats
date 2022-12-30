package io.pakland.mdas.githubstats.infrastructure.shell.model;

import io.pakland.mdas.githubstats.infrastructure.shell.components.ShellComponent;
import org.springframework.shell.command.CommandRegistration;

public class CommandRegistrationBuilder {

    private final String command;
    private final String description;
    private final String nameLabel;

    public CommandRegistrationBuilder(String command, String description, String nameLabel) {
        this.command = command;
        this.description = description;
        this.nameLabel = nameLabel;
    }

    public CommandRegistration build(ShellComponent shellComponent) {
        return CommandRegistration.builder()
            .command(this.command)
            .description(this.description)
            .withTarget()
            .method(shellComponent, "run")
            .and()
            .withOption()
            .shortNames('n')
            .longNames("name")
            .label(this.nameLabel)
            .arity(CommandRegistration.OptionArity.EXACTLY_ONE)
            .type(String.class)
            .required()
            .and()
            .withOption()
            .shortNames('k')
            .longNames("key")
            .label("API_KEY")
            .arity(CommandRegistration.OptionArity.EXACTLY_ONE)
            .type(String.class)
            .required()
            .and()
            .withOption()
            .longNames("from")
            .label("<from-date>")
            .description(
                "Date should be entered as MM/yy. We will parse the date to match the first day of the given month.")
            .arity(CommandRegistration.OptionArity.EXACTLY_ONE)
            .type(String.class)
            .required()
            .and()
            .withOption()
            .longNames("to")
            .label("<to-date>")
            .description(
                "Date should be entered as MM/yy. We will parse the date to match the first day of the given month.")
            .arity(CommandRegistration.OptionArity.EXACTLY_ONE)
            .type(String.class)
            .required()
            .and()
            .build();
    }
}
