package io.pakland.mdas.githubstats.infrastructure.shell.components;

import io.pakland.mdas.githubstats.domain.OptionType;

public class ShellUserComponent extends ShellComponent {
    @Override
    public OptionType getType() {
        return OptionType.USER;
    }
}
