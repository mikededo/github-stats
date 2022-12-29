package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Team;
import io.pakland.mdas.githubstats.domain.User;

import java.util.List;

public interface UserExternalRepository {
    List<User> fetchUsersFromTeam(Team team) throws HttpException;
}
