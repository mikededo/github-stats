package io.pakland.mdas.githubstats.infrastructure.shell.components;

import io.pakland.mdas.githubstats.domain.OptionType;
import io.pakland.mdas.githubstats.infrastructure.controller.MainController;
import org.springframework.stereotype.Component;

@Component
public class ShellTeamComponent extends ShellComponent {

    public ShellTeamComponent(MainController mainController) {
        super(mainController);
    }

    @Override
    public OptionType getType() {
        return OptionType.TEAM;
    }
}
