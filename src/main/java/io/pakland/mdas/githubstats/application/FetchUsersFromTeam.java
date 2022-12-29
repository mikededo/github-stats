package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.domain.User;
import io.pakland.mdas.githubstats.domain.repository.UserExternalRepository;

import java.util.List;

public class FetchUsersFromTeam {

    private final UserExternalRepository userExternalRepository;


    public FetchUsersFromTeam(UserExternalRepository userExternalRepository) {
        this.userExternalRepository = userExternalRepository;
    }

    public List<User> execute(Team team) throws HttpException {
        List<User> users = this.userExternalRepository.fetchUsersFromTeam(team);
        team.addUsers(users);
        return users;
    }
}
