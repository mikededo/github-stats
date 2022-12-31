package io.pakland.mdas.githubstats.infrastructure.shell.components;

import io.pakland.mdas.githubstats.domain.OptionType;

public class ShellOrganizationComponent extends ShellComponent {
    @Override
    public OptionType getType() {
        return OptionType.ORGANIZATION;
    }
}
