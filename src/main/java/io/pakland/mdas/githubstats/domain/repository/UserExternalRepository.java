package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Team;
import io.pakland.mdas.githubstats.domain.entity.User;

import java.util.List;

public interface UserExternalRepository {
    List<User> fetchUsersFromTeam(Team team) throws HttpException;
}
