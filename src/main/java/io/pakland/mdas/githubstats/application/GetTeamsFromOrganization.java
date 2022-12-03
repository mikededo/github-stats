package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.model.Organization;
import io.pakland.mdas.githubstats.domain.model.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetTeamsFromOrganization {
    public GetTeamsFromOrganization() { }

    @Transactional
    public List<Team> execute(Organization org) {
        return org.getTeams();
    }
}
