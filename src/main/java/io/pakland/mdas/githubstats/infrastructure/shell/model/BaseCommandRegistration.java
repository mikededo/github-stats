package io.pakland.mdas.githubstats.infrastructure.shell.model;

import io.pakland.mdas.githubstats.infrastructure.shell.components.ShellComponent;
import org.springframework.shell.command.CommandRegistration;

public class BaseCommandRegistration implements CommandRegistrationBuilder {

    private final CommandRegistration.Builder builder;
    private final ShellComponent shellComponent;
    private final String command;
    private final String description;
    private final String nameLabel;

    public BaseCommandRegistration(ShellComponent shellComponent, String command, String description, String nameLabel) {
        this.builder = CommandRegistration.builder();
        this.shellComponent = shellComponent;
        this.command = command;
        this.description = description;
        this.nameLabel = nameLabel;
    }

    @Override
    public CommandRegistration.Builder generate() {
        return this.builder
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
            .and();
    }
}
