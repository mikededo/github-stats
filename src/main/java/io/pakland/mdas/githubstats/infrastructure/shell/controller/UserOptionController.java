package io.pakland.mdas.githubstats.infrastructure.shell.controller;

import io.pakland.mdas.githubstats.application.FetchAvailableOrganizations;
import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.WebClientConfiguration;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.OrganizationRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.ports.IOrganizationRESTRepository;
import io.pakland.mdas.githubstats.infrastructure.shell.model.UserOptionRequest;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class UserOptionController {
    private UserOptionRequest userOptionRequest;
    private IOrganizationRESTRepository organizationRESTRepository;

    Logger logger = LoggerFactory.getLogger(UserOptionController.class);

    public UserOptionController(UserOptionRequest userOptionRequest) {
        this.userOptionRequest = userOptionRequest;
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration("https://api.github.com", userOptionRequest.getApiKey());
        this.organizationRESTRepository = new OrganizationRESTRepository(webClientConfiguration);
    }

    public void execute() {

        FetchAvailableOrganizations fetchAvailableOrganizations = new FetchAvailableOrganizations(this.organizationRESTRepository);
        try {
            List<OrganizationDTO> organizationDTOList = fetchAvailableOrganizations.fetch();
            logger.info(organizationDTOList.toString());
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }

}
