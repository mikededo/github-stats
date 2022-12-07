package io.pakland.mdas.githubstats.infrastructure.shell.configuration;

import io.pakland.mdas.githubstats.infrastructure.shell.components.TeamComponent;
import io.pakland.mdas.githubstats.infrastructure.shell.components.UserOptionComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.command.CommandRegistration;

@Configuration
public class CommandConfiguration {

    @Bean
    public CommandRegistration buildUserCommand() {
        UserOptionComponent userOptionComponent = new UserOptionComponent();

        return CommandRegistration.builder()
                .command("user")
                .description("Get data from a specified user.")
            .withTarget()
                .method(userOptionComponent, "user")
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

    @Bean
    public CommandRegistration buildTeamCommand() {
        TeamComponent teamComponent = new TeamComponent();

        return CommandRegistration.builder()
                .command("team")
                .description("Get data from a specified team and its sub teams.")
            .withTarget()
                .method(teamComponent, "team")
                .and()
            .withOption()
                .shortNames('n')
                .longNames("name")
                .label("TEAM_NAME")
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
