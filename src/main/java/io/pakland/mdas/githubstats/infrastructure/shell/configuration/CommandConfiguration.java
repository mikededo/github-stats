package io.pakland.mdas.githubstats.infrastructure.shell.configuration;

import io.pakland.mdas.githubstats.infrastructure.shell.components.ShellController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.command.CommandRegistration;

@Configuration
public class CommandConfiguration {

    private final ShellController shellController;

    public CommandConfiguration(ShellController shellController) {
        this.shellController = shellController;
    }

    @Bean
    public CommandRegistration buildUserCommand() {

        return CommandRegistration.builder()
                .command("user")
                .description("Get data from a specified user.")
            .withTarget()
                .method(shellController, "user")
                .and()
            .withOption()
                .shortNames('n')
                .longNames("name")
                .label("USER_NAME")
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
                .label("FROM_DATE")
                .arity(CommandRegistration.OptionArity.EXACTLY_ONE)
                .type(String.class)
                .required()
                .and()
            .withOption()
                .longNames("to")
                .label("TO_DATE")
                .arity(CommandRegistration.OptionArity.EXACTLY_ONE)
                .type(String.class)
                .required()
                .and()
            .build();
    }
}
