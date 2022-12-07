package io.pakland.mdas.githubstats.infrastructure.shell.controller;

import io.pakland.mdas.githubstats.infrastructure.rest.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.OrganizationRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.IOrganizationRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;
import org.springframework.stereotype.Component;

@Component
public class UserOptionController {
    private UserOptionRequest userOptionRequest;
    private IOrganizationRESTRepository organizationRESTRepository;

    public UserOptionController(UserOptionRequest userOptionRequest) {
        this.userOptionRequest = userOptionRequest;
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration("https://api.github.com", "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");
        this.organizationRESTRepository = new OrganizationRESTRepository(webClientConfiguration);
    }

    public void execute() {

    }

}
