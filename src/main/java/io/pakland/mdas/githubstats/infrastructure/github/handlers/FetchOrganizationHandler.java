package io.pakland.mdas.githubstats.infrastructure.github.handlers;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.application.external.FetchAvailableOrganizations;
import io.pakland.mdas.githubstats.application.handlers.Request;
import io.pakland.mdas.githubstats.application.handlers.RequestHandler;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.infrastructure.github.handlers.request.NullRequest;
import io.pakland.mdas.githubstats.infrastructure.github.handlers.request.OrganizationRequest;
import io.pakland.mdas.githubstats.infrastructure.github.repository.OrganizationGitHubRepository;
import io.pakland.mdas.githubstats.infrastructure.github.repository.WebClientConfiguration;
import java.util.ArrayList;
import java.util.List;

public class FetchOrganizationHandler implements RequestHandler {

    private final WebClientConfiguration webConfig;
    private final List<RequestHandler> next;

    public FetchOrganizationHandler(WebClientConfiguration config) {
        this.webConfig = config;
        this.next = new ArrayList<>();
    }

    @Override
    public FetchOrganizationHandler addNext(RequestHandler handler) {
        this.next.add(handler);
        return this;
    }

    @Override
    public void handle(Request request) {
        if (!(request instanceof NullRequest)) {
            throw new RuntimeException(
                "FetchOrganizationHandler::handle called with request different of type NullRequest");
        }

        try {
            List<Organization> organizationList = new FetchAvailableOrganizations(
                new OrganizationGitHubRepository(this.webConfig)
            ).execute();

            // Run each handler
            organizationList.forEach(organization -> {
                this.next.forEach(handler -> handler.handle(new OrganizationRequest(organization)));
            });
        } catch (HttpException e) {
            throw new RuntimeException(e);
        }
    }
}
