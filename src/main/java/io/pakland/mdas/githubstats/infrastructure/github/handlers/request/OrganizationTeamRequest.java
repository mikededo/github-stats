package io.pakland.mdas.githubstats.infrastructure.github.handlers.request;

import io.pakland.mdas.githubstats.application.handlers.Request;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Team;
import org.springframework.data.util.Pair;

public class OrganizationTeamRequest implements Request {

    private final Organization organization;
    private final Team team;

    public OrganizationTeamRequest(Organization organization, Team team) {
        this.organization = organization;
        this.team = team;
    }

    @Override
    public Pair<Organization, Team> getData() {
        return Pair.of(organization, team);
    }
}
