package io.pakland.mdas.githubstats.infrastructure.shell.configuration;

import io.pakland.mdas.githubstats.infrastructure.controller.MainController;
import io.pakland.mdas.githubstats.infrastructure.shell.components.ShellOrganizationComponent;
import io.pakland.mdas.githubstats.infrastructure.shell.components.ShellTeamComponent;
import io.pakland.mdas.githubstats.infrastructure.shell.components.ShellUserComponent;
import io.pakland.mdas.githubstats.infrastructure.shell.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.command.CommandRegistration;

@Configuration
public class CommandConfiguration {

    private final MainController mainController;

    public CommandConfiguration(MainController mainController) {
        this.mainController = mainController;
    }

    @Bean
    public CommandRegistration buildUserCommand() {
        CommandRegistrationBuilder builder = new BaseCommandRegistration(
            new ShellUserComponent(mainController),
            "user",
            "Load data from the specified user",
            "<user-name>"
        );

        return new LoggerSilencerCommandRegistration(
            new FileNameCommandRegistration(
                new ToDateCommandRegistration(new FromDateCommandRegistration(builder)),
                "output-user.csv"
            )
        ).generate().build();
    }

    @Bean
    public CommandRegistration buildTeamCommand() {
        CommandRegistrationBuilder builder = new BaseCommandRegistration(
            new ShellTeamComponent(mainController),
            "team",
            "Load data from all users of the specified team",
            "<team-name>"
        );

        return new LoggerSilencerCommandRegistration(
            new FileNameCommandRegistration(
                new ToDateCommandRegistration(new FromDateCommandRegistration(builder)),
                "output-team.csv"
            )
        ).generate().build();
    }

    @Bean
    public CommandRegistration buildOrganizationCommand() {
        CommandRegistrationBuilder builder = new BaseCommandRegistration(
            new ShellOrganizationComponent(mainController),
            "organization",
            "Load data from all users of the specified organization, grouped by their team",
            "<organization-name>"
        );

        return new LoggerSilencerCommandRegistration(
            new FileNameCommandRegistration(
                new ToDateCommandRegistration(new FromDateCommandRegistration(builder)),
                "output-organization.csv"
            )
        ).generate().build();
    }
}
