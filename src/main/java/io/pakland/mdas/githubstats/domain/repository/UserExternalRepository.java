package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.User;

import java.util.List;

public interface UserExternalRepository {
    List<User> fetchUsersFromTeam(String organizationName, String teamName) throws HttpException;
}
