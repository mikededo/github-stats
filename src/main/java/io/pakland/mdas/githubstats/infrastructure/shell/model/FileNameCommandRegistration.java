package io.pakland.mdas.githubstats.infrastructure.shell.model;

import org.springframework.shell.command.CommandRegistration;
import org.springframework.shell.command.CommandRegistration.OptionArity;

public class FileNameCommandRegistration implements CommandRegistrationBuilder {

    private final CommandRegistrationBuilder builder;
    private final String defaultName;

    public FileNameCommandRegistration(CommandRegistrationBuilder builder, String defaultName) {
        this.builder = builder;
        this.defaultName = defaultName;
    }

    @Override
    public CommandRegistration.Builder generate() {
        return this.builder
            .generate()
            .withOption()
            .shortNames('p')
            .longNames("path")
            .label("<path>")
            .description(
                "Path of the file to which the output will be generated. If file exists it will be overwritten.")
            .arity(OptionArity.ZERO_OR_ONE)
            .type(String.class)
            .defaultValue(defaultName)
            .and();
    }

}
