package io.pakland.mdas.githubstats.infrastructure.shell.configuration;

import io.pakland.mdas.githubstats.infrastructure.shell.components.ShellOrganizationComponent;
import io.pakland.mdas.githubstats.infrastructure.shell.components.ShellTeamComponent;
import io.pakland.mdas.githubstats.infrastructure.shell.components.ShellUserComponent;
import io.pakland.mdas.githubstats.infrastructure.shell.model.CommandRegistrationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.command.CommandRegistration;

@Configuration
public class CommandConfiguration {


    @Bean
    public CommandRegistration buildUserCommand() {
        return new CommandRegistrationBuilder(
            "user",
            "Load data from the specified user",
            "<user-name>"
        ).build(new ShellUserComponent());
    }

    @Bean
    public CommandRegistration buildTeamCommand() {
        return new CommandRegistrationBuilder(
            "team",
            "Load data from all users of the specified team",
            "<team-name>"
        ).build(new ShellTeamComponent());
    }

    @Bean
    public CommandRegistration buildOrganizationCommand() {
        return new CommandRegistrationBuilder(
            "organization",
            "Load data from all users of the specified organization, grouped by their team",
            "<organization-name>"
        ).build(new ShellOrganizationComponent());
    }
}
