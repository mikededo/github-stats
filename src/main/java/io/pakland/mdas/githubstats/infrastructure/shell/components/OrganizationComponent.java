package io.pakland.mdas.githubstats.infrastructure.shell.components;

import io.pakland.mdas.githubstats.domain.service.GetOrganizationFromId;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class OrganizationComponent {

    final private GetOrganizationFromId getOrganizationFromId;

    public OrganizationComponent(GetOrganizationFromId getOrganizationFromId) {
        this.getOrganizationFromId = getOrganizationFromId;
    }

    @ShellMethod(key = "getOrg", value = "get organization info")
    public void getOrg(@ShellOption(value = "--id") Long organizationId) {
        getOrganizationFromId.execute(organizationId);
    }

}
